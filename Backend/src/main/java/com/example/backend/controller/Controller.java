package com.example.backend.controller;

import com.example.backend.model.DirectoryContents;
import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import com.example.backend.service.ImageService;
import com.example.backend.service.ThumbnailService;
import com.example.backend.watcher.DirectoryWatcher;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@RestController
public class Controller {

    private final ThumbnailService thumbnailService;
    private final ImageService imageService;

    private final DirectoryWatcher directoryWatcher;

    public Controller(ThumbnailService thumbnailService, ImageService imageService, DirectoryWatcher directoryWatcher) throws IOException {
        this.thumbnailService = thumbnailService;
        this.imageService = imageService;
        this.directoryWatcher = directoryWatcher;
        directoryWatcher.watch();
    }

    @GetMapping(path = "{id}")
    public Single<ResponseEntity<Image>> getImage(@PathVariable int id) {
        return imageService.getImage(id).subscribeOn(Schedulers.io())
                .map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/path")
    public Single<ResponseEntity<DirectoryContents>> getImage(@RequestParam String path) {
        return thumbnailService.getPathContents(path).subscribeOn(Schedulers.io())
                .map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .onErrorReturnItem(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping(path = "thumbnail/{id}")
    public Single<ResponseEntity<Thumbnail>> getThumbnail(@PathVariable int id) {
        log.info("GOT REQUEST");
        return thumbnailService.getThumbnail(id).subscribeOn(Schedulers.io())
                .map(res -> res.isEmpty() || !res.get().isComplete()
                        ? new ResponseEntity<>(new Thumbnail(), HttpStatus.PROCESSING)
                        : new ResponseEntity<>(res.get(), HttpStatus.OK))
                .onErrorReturnItem(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public Single<ResponseEntity<Integer>> postImage(@RequestBody Image image) {
        thumbnailService.generateThumbnail(image);
        return imageService.uploadImage(image).subscribeOn(Schedulers.io())
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                .onErrorReturnItem(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

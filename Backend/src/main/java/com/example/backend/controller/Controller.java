package com.example.backend.controller;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import com.example.backend.service.ImageService;
import com.example.backend.service.ThumbnailService;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("image")
public class Controller {

    private final ThumbnailService thumbnailService;
    private final ImageService imageService;

    //TODO wrap responses in response object
    //TODO wrap requests in request object
    //TODO what if sth goes wrong?

    public Controller(ThumbnailService thumbnailService, ImageService imageService) {
        this.thumbnailService = thumbnailService;
        this.imageService = imageService;
    }

    @GetMapping(path = "{id}")
    public Single<ResponseEntity<Image>> getImage(@PathVariable int id) {
        return imageService.getImage(id).subscribeOn(Schedulers.io())
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK));
    }

    @GetMapping(path = "thumbnail/{id}")
    public Single<ResponseEntity<Thumbnail>> getThumbnail(@PathVariable int id) {
        return thumbnailService.getThumbnail(id).subscribeOn(Schedulers.io())
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK));
    }

    @PostMapping()
    public Single<ResponseEntity<Integer>> postImage(@RequestBody Image image) {

        return imageService.uploadImage(image).subscribeOn(Schedulers.io())
                .map(id -> new ResponseEntity<>(id, HttpStatus.OK));
    }
}

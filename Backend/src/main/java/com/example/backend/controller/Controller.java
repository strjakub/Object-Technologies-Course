package com.example.backend.controller;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import com.example.backend.service.ImageService;
import com.example.backend.service.ThumbnailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class Controller {

    private final ThumbnailService thumbnailService;
    private final ImageService imageService;

    private final ObjectMapper mapper = new ObjectMapper();

    public Controller(ThumbnailService thumbnailService, ImageService imageService) {
        this.thumbnailService = thumbnailService;
        this.imageService = imageService;
    }

    @GetMapping(path = "{id}")
    public Single<ResponseEntity<String>> getImage(@PathVariable int id) {
        return imageService.getImage(id).subscribeOn(Schedulers.io())
                .map(r -> new ResponseEntity<>(mapper.writeValueAsString(r), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "thumbnail/{id}")
    public Single<ResponseEntity<String>> getThumbnail(@PathVariable int id) {

//        Maybe<Thumbnail> res = thumbnailService.getThumbnail(id).subscribeOn(Schedulers.computation());
//        return imageService.getImage(id)
//                .map(i -> thumbnailService.getThumbnail(i))
//                .map()
//                .defaultIfEmpty(new ResponseEntity<>(null, HttpStatus.NOT_FOUND))
        return null;
    }

    @PostMapping()
    public Single<ResponseEntity<Integer>> postImage(@RequestBody Image image) {
        thumbnailService.generateThumbnail(image);
        return imageService.uploadImage(image).subscribeOn(Schedulers.io())
                .map(res -> new ResponseEntity<>(res, HttpStatus.OK))
                .onErrorReturnItem(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

package com.example.backend.controller;

import com.example.backend.model.Image;
import com.example.backend.service.ImageService;
import com.example.backend.service.ThumbnailService;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                .map(res -> new ResponseEntity<>(mapper.writeValueAsString(res), HttpStatus.OK))
                .onErrorReturnItem(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "thumbnail/{id}")
    public Single<ResponseEntity<String>> getThumbnail(@PathVariable int id) {
        return thumbnailService.getThumbnail(id).subscribeOn(Schedulers.io())
                .map(res -> new ResponseEntity<>(mapper.writeValueAsString(res), HttpStatus.OK))
                .onErrorReturnItem(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public Single<ResponseEntity<Integer>> postImage(@RequestBody Image image) {
        return Single.zip(imageService.uploadImage(image).subscribeOn(Schedulers.io()),
                thumbnailService.generateThumbnail(image).subscribeOn(Schedulers.io()),
                (a, b) -> new ResponseEntity<>(a, HttpStatus.OK));
    }
}

package com.example.backend.controller;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import com.example.backend.service.ThumbnailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("thumbnail")
public class ThumbnailController {

    private ThumbnailService service;

    public ThumbnailController(ThumbnailService service) {
        this.service = service;
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Thumbnail> getImage(@PathVariable String id) {
        return new ResponseEntity<>(service.getThumbnail(Integer.parseInt(id)), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Integer> postImage(@RequestBody Image imageToBeProcessed) {
        int id = service.uploadPhoto(imageToBeProcessed);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}

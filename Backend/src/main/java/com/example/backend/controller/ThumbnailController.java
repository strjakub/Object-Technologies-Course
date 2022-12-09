package com.example.backend.controller;

import com.example.backend.model.Thumbnail;
import com.example.backend.service.ThumbnailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
public class ThumbnailController {

    private ThumbnailService service;

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<Thumbnail> getImage(@PathVariable String id) {
        return new ResponseEntity<>(new Thumbnail(), HttpStatus.OK);
    }

    @PostMapping("post")
    public ResponseEntity<Integer> postImage(@RequestBody Image imageToBeProcessed) {
        int id = 0;
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}

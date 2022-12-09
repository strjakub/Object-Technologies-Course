package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import org.springframework.stereotype.Service;

@Service
public class ThumbnailService {

    public int uploadPhoto(Image image) {
        return 0;
    }

    public Thumbnail getThumbnail(int id) {
        return new Thumbnail();
    }

    private int generateId() {
        return 0;
    }
}

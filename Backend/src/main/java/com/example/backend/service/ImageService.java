package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.repositories.ImageRepository;
import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;


    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Single<Integer> uploadImage(Image image) {
        return Single.fromCallable(() -> imageRepository.save(image).getId());
    }

}

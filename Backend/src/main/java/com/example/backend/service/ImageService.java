package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.repositories.ImageRepository;
import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;


    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Single<Integer> uploadImage(Image image) {
        return Single.create(subscriber -> {
            Image res = imageRepository.save(image);
            subscriber.onSuccess(res.getId());
        });
    }

    public Single<Image> getImage(int id) {
        return Single.create(subscriber -> {
            Optional<Image> res = imageRepository.findById(id);
            if (res.isEmpty()) {
                throw new EntityNotFoundException();
            }
            subscriber.onSuccess(res.get());
        });
    }
}

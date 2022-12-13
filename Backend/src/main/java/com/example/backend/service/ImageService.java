package com.example.backend.service;

import com.example.backend.dao.ImageDAO;
import com.example.backend.model.Image;
import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ImageService {

    ImageDAO imageDAO;

    public ImageService(ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }

    public Single<Integer> uploadImage(Image image) {
        return Single.create(subscriber -> {
            Optional<Image> res = imageDAO.create(image.getData(), image.getExtension());
            if (res.isEmpty()) {
                subscriber.onError(new EntityNotFoundException());
            }
            subscriber.onSuccess(res.get().getId());
        });
    }

    public Single<Image> getImage(int id) {
        return Single.create(subscriber -> {
            Optional<Image> res = imageDAO.findById(id);
            if (res.isEmpty()) {
                subscriber.onError(new EntityNotFoundException());
            }
            subscriber.onSuccess(res.get());
        });
    }
}

package com.example.backend.service;

import com.example.backend.dao.ImageDAO;
import com.example.backend.dao.ThumbnailDAO;
import com.example.backend.model.Image;
import com.example.backend.util.ThumbnailGenerator;
import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ImageService {

    private final ThumbnailGenerator generator;

    private final ImageDAO imageDAO;

    private final ThumbnailDAO thumbnailDAO;

    public ImageService(ThumbnailGenerator generator, ImageDAO imageDAO, ThumbnailDAO thumbnailDAO) {
        this.generator = generator;
        this.imageDAO = imageDAO;
        this.thumbnailDAO = thumbnailDAO;
    }

    public Single<Integer> uploadImage(Image image) {
        return Single.create(subscriber -> {
            Optional<Image> res = imageDAO.create(image.getData(), image.getExtension());
            if (res.isEmpty()) {
                subscriber.onError(new EntityNotFoundException());
            }
            thumbnailDAO.create(generator.convertToThumbnail(image), image.getExtension(), image);
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

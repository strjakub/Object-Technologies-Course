package com.example.backend.service;

import com.example.backend.dao.ImageDAO;
import com.example.backend.dao.ThumbnailDAO;
import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import com.example.backend.util.ThumbnailGenerator;
import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ThumbnailService {

    private final ImageDAO imageDAO;

    private final ThumbnailDAO thumbnailDAO;

    private final ThumbnailGenerator generator;

    public ThumbnailService(ImageDAO imageDAO, ThumbnailDAO thumbnailDAO, ThumbnailGenerator generator) {
        this.imageDAO = imageDAO;
        this.thumbnailDAO = thumbnailDAO;
        this.generator = generator;
    }

    public Single<Thumbnail> getThumbnail(int id) {
        return Single.create(subscriber -> {
            Optional<Image> res = imageDAO.findById(id);
            if (res.isEmpty()) {
                subscriber.onError(new EntityNotFoundException());
            }
        });
    }

    public Single<Thumbnail> generateThumbnail(Image img) {
        return Single.create(subscriber -> {
            Optional<Thumbnail> res = thumbnailDAO.create(generator.convertToThumbnail(img), img.getExtension(), img);
            if (res.isEmpty()) {
                subscriber.onError(new EntityNotFoundException());
            }
            subscriber.onSuccess(res.get());
        });
    }
}

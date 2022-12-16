package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import com.example.backend.repositories.ImageRepository;
import com.example.backend.repositories.ThumbnailRepository;
import com.example.backend.util.ThumbnailGenerator;
import io.reactivex.rxjava3.core.Single;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ThumbnailService {

    private final ThumbnailRepository thumbnailRepository;

    private final ImageRepository imageRepository;
    private final ThumbnailGenerator generator;

    public ThumbnailService(ThumbnailRepository thumbnailRepository, ImageRepository imageRepository, ThumbnailGenerator generator) {
        this.thumbnailRepository = thumbnailRepository;
        this.imageRepository = imageRepository;
        this.generator = generator;
    }

    public Single<Optional<Thumbnail>> getThumbnail(int id) {
        return Single.create(subscriber -> {
            Optional<Image> img = imageRepository.findById(id);
            if (img.isEmpty()) {
                throw new EntityNotFoundException();
            }
            Optional<Thumbnail> res = thumbnailRepository.findByImage_Id(img.get());
            if (res.isEmpty()) {
                subscriber.onSuccess(Optional.empty());
                return;
            }
            subscriber.onSuccess(Optional.of(res.get()));
        });
    }

    public Single<Thumbnail> generateThumbnail(Image img) {
        return Single.create(subscriber -> {
            Thumbnail thumbnail = new Thumbnail(generator.convertToThumbnail(img), img.getExtension(), img);
            Thumbnail res = thumbnailRepository.save(thumbnail);
//            if (res.isEmpty()) {
//                throw new EntityNotFoundException();
//            }
            subscriber.onSuccess(res);
        });
    }
}

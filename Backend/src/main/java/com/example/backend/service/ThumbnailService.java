package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import com.example.backend.repositories.ImageRepository;
import com.example.backend.repositories.ThumbnailRepository;
import com.example.backend.utils.Size;
import com.example.backend.utils.ThumbnailGenerator;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Slf4j
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

    public void generateThumbnail(Image img) {
        Completable.fromAction(() -> {
            Thumbnail thumbnail = new Thumbnail(null, null, null, img.getExtension(), img.getPath(), img);
            thumbnailRepository.save(thumbnail);
            byte[] small = generator.convertToThumbnail(img, Size.SMALL);
            thumbnail.setSmall(small);
            byte[] medium = generator.convertToThumbnail(img, Size.MEDIUM);
            thumbnail.setMedium(medium);
            byte[] large = generator.convertToThumbnail(img, Size.LARGE);
            thumbnail.setSmall(large);
        }).subscribeOn(Schedulers.computation()).subscribe();
    }
}

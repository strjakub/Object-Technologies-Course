package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import com.example.backend.model.ThumbnailOption;
import com.example.backend.repositories.ImageRepository;
import com.example.backend.repositories.ThumbnailRepository;
import com.example.backend.utils.ThumbnailGenerator;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public Maybe<Thumbnail> getThumbnail(Image image) {
        return Maybe.fromCallable(() -> {
                    Optional<Image> img = imageRepository.findById(id);
                    if (img.isPresent()) {
                        return new ThumbnailOption(img, ValidQuery.TRUE);
                    } else {
                        return new ThumbnailOption(img, ValidQuery.TRUE);
                    }
                })
                .flatMap(Maybe::fromOptional)
//                .flatMap((img) -> Maybe.fromOptional(thumbnailRepository.findByImage_Id(img)));
    }

    public void generateThumbnail(Image img) {
        Completable.fromAction(() -> {}).subscribeOn(Schedulers.computation()).subscribe();
    }
}

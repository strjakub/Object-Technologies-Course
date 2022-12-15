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
public class ImageService {

    private final ThumbnailGenerator generator;

    private final ImageRepository imageRepository;

    private final ThumbnailRepository thumbnailRepository;

//    private final ImageDAO imageDAO;
//
//    private final ThumbnailDAO thumbnailDAO;

    public ImageService(ImageRepository imageRepository, ThumbnailRepository thumbnailRepository, ThumbnailGenerator generator) {
        this.generator = generator;
        this.imageRepository = imageRepository;
        this.thumbnailRepository = thumbnailRepository;
//        this.imageDAO = imageDAO;
//        this.thumbnailDAO = thumbnailDAO;
    }

    public Single<Integer> uploadImage(Image image) {
        imageRepository.save(image);
        return Single.create(subscriber -> {
            thumbnailRepository.save(new Thumbnail(generator.convertToThumbnail(image), image.getExtension(), image));
            subscriber.onSuccess(image.getId());
        });
    }

    public Single<Image> getImage(int id) {
        return Single.create(subscriber -> {
            SessionService.openSession();
            Optional<Image> res = imageRepository.findById(id);
            if (res.isEmpty()) {
                subscriber.onError(new EntityNotFoundException());
            }
            SessionService.closeSession();
            subscriber.onSuccess(res.get());
        });
    }
}

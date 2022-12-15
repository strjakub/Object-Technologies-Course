package com.example.backend.service;

import com.example.backend.dao.ImageDAO;
import com.example.backend.dao.ThumbnailDAO;
import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import com.example.backend.repositories.ImageRepository;
import com.example.backend.repositories.ThumbnailRepository;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ThumbnailService {

    private final ImageRepository imageRepository;

    public ThumbnailService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Single<Thumbnail> getThumbnail(int id) {
        return Single.create(subscriber -> {
            SessionService.openSession();
            Optional<Image> res = imageRepository.findById(id);
            if (res.isEmpty()) {
                subscriber.onError(new EntityNotFoundException());
            }
//            subscriber.onSuccess(res.get().getThumbnail());
        });
    }
}

package com.example.backend.service;

import com.example.backend.model.Directory;
import com.example.backend.model.DirectoryContents;
import com.example.backend.model.Image;
import com.example.backend.model.Thumbnail;
import com.example.backend.repositories.DirectoryRepository;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ThumbnailService {

    private final ThumbnailRepository thumbnailRepository;
    private final ImageRepository imageRepository;
    private final DirectoryRepository directoryRepository;
    private final ThumbnailGenerator generator;

    public ThumbnailService(ThumbnailRepository thumbnailRepository, ImageRepository imageRepository, ThumbnailGenerator generator, DirectoryRepository directoryRepository) {
        this.thumbnailRepository = thumbnailRepository;
        this.imageRepository = imageRepository;
        this.directoryRepository = directoryRepository;
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

    public Single<DirectoryContents> getPathContents(String path){
        return Single.create(subscriber -> {
            int inDepth = path.split("/").length;
            Collection<Thumbnail> thumbnails = thumbnailRepository.findAllThumbnailsByPath(path);
            Collection<String> directories = directoryRepository.findByPathStartsWith(path).stream()
                    .map(Directory::getPath)
                    .map(x -> x.split("/"))
                    .filter(x -> x.length > inDepth)
                    .map(x -> x[inDepth])
                    .distinct()
                    .toList();
            subscriber.onSuccess(new DirectoryContents(thumbnails, directories));
        });
    }

    public void generateThumbnail(Image img) {
        generateThumbnail(img, true, true, true);
    }

    public void generateThumbnail(Image img, boolean smallNeeded, boolean mediumNeeded, boolean largeNeeded) {
        Completable.fromAction(() -> {
            Thumbnail thumbnail;
            if(smallNeeded) {
                byte[] small = generator.convertToThumbnail(img, Size.SMALL);
                thumbnail = new Thumbnail(small, null, null, img.getExtension(), img.getPath(), img);
                thumbnailRepository.save(thumbnail);
            } else {
                thumbnail = thumbnailRepository.findByImage_Id(img).get();
            }
            if(mediumNeeded) {
                byte[] medium = generator.convertToThumbnail(img, Size.MEDIUM);
                thumbnail.setMedium(medium);
                thumbnailRepository.save(thumbnail);
            }
            if(largeNeeded) {
                byte[] large = generator.convertToThumbnail(img, Size.LARGE);
                thumbnail.setLarge(large);
                thumbnailRepository.save(thumbnail);
            }
            log.info("Ended generating thumbnails");
        }).subscribeOn(Schedulers.computation()).subscribe();
    }
}

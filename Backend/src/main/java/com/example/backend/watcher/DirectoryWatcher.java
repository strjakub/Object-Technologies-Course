package com.example.backend.watcher;

import com.example.backend.BackendApplication;
import com.example.backend.model.Image;
import com.example.backend.service.ImageService;
import com.example.backend.service.ThumbnailService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

@Slf4j
@Component
public class DirectoryWatcher {

    private final ImageService imageService;

    private final ThumbnailService thumbnailService;

    private final WatchService watchService;

    private final Path resources;

    private final List<String> validExtensions = List.of("jpg", "png");

    @SneakyThrows
    public DirectoryWatcher(ImageService imageService, ThumbnailService thumbnailService) {
        this.resources = Paths.get(DirectoryWatcher.class.getResource("Images").toURI());
        this.watchService = FileSystems.getDefault().newWatchService();
        this.imageService = imageService;
        this.thumbnailService = thumbnailService;
    }

    public Observable<WatchEvent<?>> getSource() throws IOException {
        resources.register(watchService, ENTRY_CREATE, ENTRY_DELETE);
        return Observable.create(emitter -> {
            WatchKey key;
            while((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    log.warn("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                    emitter.onNext(event);
                }
                key.reset();
            }
            emitter.onComplete();
        });
    }

    public void watch() throws IOException {
        Observable<WatchEvent<?>> source = getSource();
        source.subscribeOn(Schedulers.io())
                .subscribe(res -> {
                   if (res.kind() == ENTRY_CREATE) {
                       if (!validExtensions.contains(FilenameUtils.getExtension(res.context().toString()))) {
                           log.warn("added file with invalid extension");
                       }
                       else {
                           Path imagePath = Paths.get(resources + "/" + res.context().toString()).toAbsolutePath();
                           byte[] data = Files.readAllBytes(imagePath);
                           String extension = FilenameUtils.getExtension(res.context().toString());
                           Image newImage = new Image(data, extension, "/server");
                           imageService.uploadImage(newImage).subscribeOn(Schedulers.computation()).subscribe();
                           thumbnailService.generateThumbnail(newImage);
                           //todo here we can add some messaging to the
                           log.info("Image saved");
                       }
                   }
                   else if (res.kind() == ENTRY_DELETE) {
                       log.info("File was deleted");
                   }
                   else {
                       log.error("Wrong type of file system event");
                   }
                });
    }
}

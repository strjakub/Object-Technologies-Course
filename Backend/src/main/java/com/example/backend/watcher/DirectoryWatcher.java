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
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
            try {
                while((key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        log.info("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                        emitter.onNext(event);
                    }
                    key.reset();
                }
            } catch (InterruptedException ie) {
                log.error("Watcher got interrupted exception");
                Thread.currentThread().interrupt();
                emitter.onComplete();
            }
        });
    }

    @Retryable(maxAttempts=5,backoff = @Backoff(delay = 3000))
    private byte[] readFile(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    public void watch() throws IOException {
        Observable<WatchEvent<?>> source = getSource();
        source.retryWhen(errors -> errors.flatMap(error -> Observable.timer(5, TimeUnit.SECONDS))).subscribeOn(Schedulers.io())
                .subscribe(res -> {
                    log.info("Watcher received event");
                   if (res.kind() == ENTRY_CREATE) {
                       if (!validExtensions.contains(FilenameUtils.getExtension(res.context().toString()))) {
                           log.warn("added file with invalid extension");
                       }
                       else {
                           Path imagePath = Paths.get(resources + "/" + res.context().toString()).toAbsolutePath();
                           Thread.sleep(1000);
                           byte[] data = readFile(imagePath);
                           String extension = FilenameUtils.getExtension(res.context().toString());
                           Image newImage = new Image(data, extension, "/server");
                           imageService.uploadImage(newImage).subscribeOn(Schedulers.computation()).subscribe();
                           thumbnailService.generateThumbnail(newImage);
                           log.info("Image saved");
                       }
                   }
                   else if (res.kind() == ENTRY_DELETE) {
                       log.info("File was deleted");
                   }
                   else {
                       log.error("Wrong type of file system event");
                   }
                }, err -> log.error(err.toString()), () -> log.info("On complete"));
    }
}

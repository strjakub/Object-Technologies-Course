package com.example.backend.watcher;

import com.example.backend.model.Image;
import com.example.backend.service.ImageService;
import com.example.backend.service.ThumbnailService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.util.Pair;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.ArrayList;
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

    private final List<Integer> watchedImageIds = new ArrayList<>();

    @SneakyThrows
    public DirectoryWatcher(ImageService imageService, ThumbnailService thumbnailService) {
        this.resources = Paths.get(DirectoryWatcher.class.getResource("Images").toURI());
        this.watchService = FileSystems.getDefault().newWatchService();
        this.imageService = imageService;
        this.thumbnailService = thumbnailService;
    }

    public Observable<Pair<WatchEvent<?>, Path>> getSource() throws IOException {
        resources.register(watchService, ENTRY_CREATE, ENTRY_DELETE);
        return Observable.create(emitter -> {
            WatchKey key;
            try {
                while ((key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        log.info("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                        Path parentPath = (Path) key.watchable();
                        if (event.kind() == ENTRY_CREATE && isFolder(event, parentPath)) {
                            parentPath.resolve(event.context().toString()).register(watchService, ENTRY_CREATE, ENTRY_DELETE);
                        }
                        emitter.onNext(Pair.of(event, parentPath));
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

    public void watch() throws IOException {
        Observable<Pair<WatchEvent<?>, Path>> source = getSource();
        source.retryWhen(errors -> errors.flatMap(error -> Observable.timer(5, TimeUnit.SECONDS))).subscribeOn(Schedulers.io())
                .subscribe(res -> {
                    WatchEvent<?> event = res.getFirst();
                    Path parentPath = res.getSecond();
                    log.info("Watcher received event");
                    if (event.kind() == ENTRY_CREATE) {
                        if (!validExtensions.contains(FilenameUtils.getExtension(event.context().toString()))) {
                            log.warn("file is not one of accepted image formats");
                        } else {
                            Path imagePath = parentPath.resolve(event.context().toString());
                            byte[] data = readFile(imagePath);
                            String extension = FilenameUtils.getExtension(event.context().toString());
                            Image newImage = new Image(data, extension, getImagePath(parentPath));
                            imageService.uploadImage(newImage).subscribeOn(Schedulers.computation()).subscribe(id -> watchedImageIds.add(id));
                            thumbnailService.generateThumbnail(newImage);
                            log.info("Image saved");
                        }
                    } else if (event.kind() == ENTRY_DELETE) {
                        log.info("File was deleted");
                    } else {
                        log.error("Wrong type of file system event");
                    }
                }, err -> log.error(err.toString()), () -> log.info("On complete"));
    }

    private boolean isFolder(WatchEvent<?> event, Path parentPath) throws MalformedURLException {
        return FileUtils.isDirectory(FileUtils.toFile(parentPath.resolve(event.context().toString()).toUri().toURL()));
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 3000))
    private byte[] readFile(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    private String getImagePath(Path path) {
        if (resources.relativize(path).toString().equals("")) {
            return ".";
        }
        return ".\\" + resources.relativize(path);
    }
}

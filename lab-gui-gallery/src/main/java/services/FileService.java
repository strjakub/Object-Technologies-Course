package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

import model.Picture;

public class FileService implements IFileService {

    private static final List<String> photosExtensions = Arrays.asList("png", "jpg", "bmp");
    private static final String ZIP = "zip";

    public boolean isPhotoOrZip(String extension) {
        return extension.equals(ZIP) || isPhoto(extension);
    }

    public boolean isPhoto(String extension) {
        return photosExtensions.contains(extension);
    }

    public String[] getExtensionFilter() {
        return Stream.concat(Stream.of(ZIP), photosExtensions.stream()).map(x -> String.format("*.%s", x)).toArray(String[]::new);
    }

    public String getExtension(String fileName) {
        var i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        }

        return "";
    }

    public void postFile(File file, NetworkCallback<Integer> callback) throws IOException {
        var absolutePath = file.getAbsolutePath();
        var extension = getExtension(absolutePath);
        FileSenderStrategyBuilder.Build(extension).sendFile(absolutePath, callback);
    }
}

interface IFileSender {
    void sendFile(String fullPath, NetworkCallback<Integer> callback) throws IOException;
}

class PhotoFileSender implements IFileSender {

    private final IFileService fileService = new FileService();
    private final IRetrofitService retrofitService = RetrofitService.Instance;

    @Override
    public void sendFile(String fullPath, NetworkCallback<Integer> callback) throws IOException {
        var extension = fileService.getExtension(fullPath);
        var path  = Paths.get(fullPath);
        var bytes = Files.readAllBytes(path);
        var image = new Picture(bytes, extension);
        retrofitService.postImage(image, callback);
    }
}

class ZipFileSender implements IFileSender {

    private final IFileService fileService = new FileService();
    private final IRetrofitService retrofitService = RetrofitService.Instance;

    @Override
    public void sendFile(String fullPath, NetworkCallback<Integer> callback) throws IOException {
        var zip = new ZipFile(fullPath);
        for (var entry: Collections.list(zip.entries())) {
            var extension =  fileService.getExtension(entry.getName());
            if (fileService.isPhoto(extension)) {
                var stream = zip.getInputStream(entry);
                var bytes = stream.readAllBytes();
                var image = new Picture(bytes, extension);
                retrofitService.postImage(image, callback);
            }
        }
        zip.close();
    }

}

class FileSenderStrategyBuilder {

    public static IFileSender Build(String extension) {
        return extension.equals("zip") ? new ZipFileSender() : new PhotoFileSender();
    }
}

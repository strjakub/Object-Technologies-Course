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

public class PhotosService implements IPhotosService {

    private static final List<String> photosExtensions = Arrays.asList("png", "jpg", "bmp");
    private static final String ZIP = "zip";

    private final IRetrofitService retrofitService = RetrofitService.Instance;

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

        if (extension.equals("zip")) {
            var zip = new ZipFile(absolutePath);
            for (var entry: Collections.list(zip.entries())) {
                var ext = getExtension(entry.getName());
                if (isPhoto(ext)) {
                    var stream = zip.getInputStream(entry);
                    var bytes = stream.readAllBytes();
                    sendOneImage(bytes, ext, callback);
                }
            }
            zip.close();
            return;
        }

        sendOneImage(absolutePath, callback);
    }

    private void sendOneImage(byte[] bytes, String extension, NetworkCallback<Integer> callback) throws IOException {
        var image = new Picture(bytes, extension);
        retrofitService.postImage(image, callback);
    }

    private void sendOneImage(String fullPath, NetworkCallback<Integer> callback) throws IOException {
        var extension = getExtension(fullPath);
        var path  = Paths.get(fullPath);
        var bytes = Files.readAllBytes(path);
        sendOneImage(bytes, extension, callback);
    }

}

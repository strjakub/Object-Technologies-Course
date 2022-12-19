package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.zip.ZipFile;

import model.Picture;

public class FileService implements IFileService {

    private final IRetrofitService retrofitService = RetrofitService.Instance;
    private final IPhotosService photosService = new PhotosService();

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
                if (photosService.isPhoto(ext)) {
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

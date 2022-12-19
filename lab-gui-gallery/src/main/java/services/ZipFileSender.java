package services;

import java.io.IOException;
import java.util.Collections;
import java.util.zip.ZipFile;

import model.Picture;

public class ZipFileSender implements IFileSender {

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

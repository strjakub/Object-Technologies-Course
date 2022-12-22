package services;

import java.io.IOException;
import java.util.Collections;
import java.util.zip.ZipFile;

import model.Picture;

public class ZipFileSender implements IFileSender {

    private final IRetrofitService retrofitService;

    public ZipFileSender(IRetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    @Override
    public void sendFile(String fullPath, NetworkCallback<Integer> callback) throws IOException {
        var zip = new ZipFile(fullPath);
        for (var entry: Collections.list(zip.entries())) {
            var extension =  File.getExtension(entry.getName());
            if (File.isPhoto(extension)) {
                var stream = zip.getInputStream(entry);
                var bytes = stream.readAllBytes();
                var image = new Picture(bytes, extension);
                retrofitService.postImage(image, callback);
            }
        }
        zip.close();
    }

}

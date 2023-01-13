package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.Picture;

public class PhotoFileSender implements IFileSender {

    private final IRetrofitService retrofitService;

    public PhotoFileSender(IRetrofitService retrofitService) {
        this.retrofitService = retrofitService;
    }

    @Override
    public void sendFile(String fullPath, String relativePath, NetworkCallback<Integer> callback) throws IOException {
        var extension = File.getExtension(fullPath);
        var path  = Paths.get(fullPath);
        var bytes = Files.readAllBytes(path);
        var image = new Picture(bytes, extension, relativePath);
        retrofitService.postImage(image, callback);
    }
}

package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.Picture;

public class PhotoFileSender implements IFileSender {

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

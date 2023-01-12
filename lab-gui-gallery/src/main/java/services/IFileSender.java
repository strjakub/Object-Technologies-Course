package services;

import java.io.IOException;

public interface IFileSender {
    void sendFile(String fullPath, String relativePath, NetworkCallback<Integer> callback) throws IOException;
}

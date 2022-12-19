package services;

import java.io.IOException;

public interface IFileSender {
    void sendFile(String fullPath, NetworkCallback<Integer> callback) throws IOException;
}

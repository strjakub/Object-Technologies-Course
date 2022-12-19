package services;

import java.io.File;
import java.io.IOException;

public interface IFileService {
    String getExtension(String fileName);
    void postFile(File file, NetworkCallback<Integer> callback) throws IOException;
}

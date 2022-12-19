package services;

import java.io.File;
import java.io.IOException;

public interface IFileService {
    boolean isPhoto(String extension);
    boolean isPhotoOrZip(String extension);
    String[] getExtensionFilter();
    String getExtension(String fileName);
    void postFile(File file, NetworkCallback<Integer> callback) throws IOException;
}

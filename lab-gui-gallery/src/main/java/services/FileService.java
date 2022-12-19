package services;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileService implements IFileService {

    private static final List<String> photosExtensions = Arrays.asList("png", "jpg", "bmp");
    public static final String ZIP = "zip";

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
        FileSenderStrategyBuilder.Build(extension).sendFile(absolutePath, callback);
    }
}

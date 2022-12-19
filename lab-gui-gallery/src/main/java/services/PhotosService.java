package services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PhotosService implements IPhotosService {

    private static final List<String> photosExtensions = Arrays.asList("png", "jpg", "bmp");
    private static final String ZIP = "zip";

    public boolean isPhotoOrZip(String extension) {
        return extension.equals(ZIP) || isPhoto(extension);
    }

    public boolean isPhoto(String extension) {
        return photosExtensions.contains(extension);
    }

    public String[] getExtensionFilter() {
        return Stream.concat(Stream.of(ZIP), photosExtensions.stream()).map(x -> String.format("*.%s", x)).toArray(String[]::new);
    }

}

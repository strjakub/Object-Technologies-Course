package services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class File {

    public static final String ZIP = "zip";
    private static final List<String> PHOTOS_EXTENSIONS = Arrays.asList("png", "jpg", "bmp");

    public static boolean isPhotoOrZip(String extension) {
        return extension.equals(ZIP) || isPhoto(extension);
    }

    public static boolean isPhoto(String extension) {
        return PHOTOS_EXTENSIONS.contains(extension);
    }

    public static String[] getExtensionFilter() {
        return Stream.concat(Stream.of(ZIP), PHOTOS_EXTENSIONS.stream()).map(x -> String.format("*.%s", x)).toArray(String[]::new);
    }

    public static String getExtension(String fileName) {
        var i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        }

        return "";
    }
}

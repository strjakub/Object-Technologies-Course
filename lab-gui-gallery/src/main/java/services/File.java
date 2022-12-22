package services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class File {

    public static final String ZIP = "zip";
    private static final List<String> photosExtensions = Arrays.asList("png", "jpg", "bmp");

    public static boolean isPhotoOrZip(String extension) {
        return extension.equals(ZIP) || isPhoto(extension);
    }

    public static boolean isPhoto(String extension) {
        return photosExtensions.contains(extension);
    }

    public static String[] getExtensionFilter() {
        return Stream.concat(Stream.of(ZIP), photosExtensions.stream()).map(x -> String.format("*.%s", x)).toArray(String[]::new);
    }

    public static String getExtension(String fileName) {
        var i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        }

        return "";
    }
}

package util;

public class ImageHelper {

    public static String getExtension(String fileName) {
        var i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i);
        }

        return "";
    }
}

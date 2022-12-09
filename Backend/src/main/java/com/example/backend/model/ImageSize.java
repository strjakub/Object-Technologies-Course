package com.example.backend.model;

public enum ImageSize {

    SMALL, MEDIUM, LARGE;

    private static final int SMALL_IMAGE_FILE_SIZE = 100 * 1024;

    private static final int MEDIUM_IMAGE_FILE_SIZE = 2000 * 1024;

    public static ImageSize resolve(Image image) {
        int imageFileSize = image.getData().length;
        if(imageFileSize <= SMALL_IMAGE_FILE_SIZE) {
            return SMALL;
        } else if (imageFileSize <= MEDIUM_IMAGE_FILE_SIZE) {
            return MEDIUM;
        }
        return LARGE;
    }

}

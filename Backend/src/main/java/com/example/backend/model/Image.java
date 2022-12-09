package com.example.backend.model;

public class Image {

    private final byte[] image;

    public Image(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }
}

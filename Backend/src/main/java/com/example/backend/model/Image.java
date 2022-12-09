package com.example.backend.model;

public class Image {

    private final byte[] data;

    public Image(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }
}

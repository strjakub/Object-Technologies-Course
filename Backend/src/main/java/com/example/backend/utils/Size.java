package com.example.backend.utils;

public enum Size {
    SMALL(300, 300),
    MEDIUM(600, 600),
    LARGE(900, 900);

    private final int width;
    private final int height;

    Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

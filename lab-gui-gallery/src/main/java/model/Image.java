package model;

public class Image {
    private final byte[] data;
    private final String extension;

    public Image(byte[] data, String extension) {
        this.data = data;
        this.extension = extension;
    }

    public byte[] getData() {
        return this.data;
    }
}

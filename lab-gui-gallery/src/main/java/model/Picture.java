package model;

public class Picture {
    
    public static final int SIZE = 100;

    private final byte[] data;
    private final String extension;

    public Picture(byte[] data, String extension) {
        this.data = data;
        this.extension = extension;
    }

    public byte[] getData() {
        return data;
    }

    public String getExtension() {
        return extension;
    }
}

package model;

public class Picture {
    
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

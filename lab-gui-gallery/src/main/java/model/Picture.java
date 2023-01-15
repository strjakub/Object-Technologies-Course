package model;

public class Picture {
    
    private final byte[] data;
    private final String extension;
    private final String path;

    public Picture(byte[] data, String extension, String path) {
        this.data = data;
        this.extension = extension;
        this.path = path;
    }

    public byte[] getData() {
        return data;
    }

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }
}

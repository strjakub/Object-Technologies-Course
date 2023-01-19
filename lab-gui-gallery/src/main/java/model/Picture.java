package model;

public class Picture {
    
    private final int id;
    private final byte[] data;
    private final String extension;
    private final String path;

    public Picture(byte[] data, String extension, String path) {
        this(-1, data, extension, path);
    }

    public Picture(int id, byte[] data, String extension, String path) {
        this.id = id;
        this.data = data;
        this.extension = extension;
        this.path = path;
    }

    public int getId() {
        return id;
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

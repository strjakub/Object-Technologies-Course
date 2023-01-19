package model;

public class Picture {
    
    private final Integer id;
    private final byte[] data;
    private final String extension;
    private final String path;

    public Picture(byte[] data, String extension, String path) {
        this(null, data, extension, path);
    }

    public Picture(Integer id, byte[] data, String extension, String path) {
        this.id = id;
        this.data = data;
        this.extension = extension;
        this.path = path;
    }

    public Integer getId() {
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

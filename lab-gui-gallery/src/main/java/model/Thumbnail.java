package model;

public class Thumbnail {

    private final int id;
    private final byte[] small;
    private final byte[] medium;
    private final byte[] large;
    private final String extension;
    private final String path;

    public Thumbnail(int id, byte[] small, byte[] medium, byte[] large, String extension, String path) {
        this.id = id;
        this.small = small;
        this.medium = medium;
        this.large = large;
        this.extension = extension;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public byte[] getImage(PictureSizes size) {
        if (size == PictureSizes.Small)
            return small;
        
        if (size == PictureSizes.Medium)
            return medium;

        if (size == PictureSizes.Large)
            return large;
        
        throw new IllegalArgumentException(size.toString());
    }

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }
}

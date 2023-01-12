package model;

public class Thumbnail {
    
    public static final int SIZE = 100;

    private final byte[] small;
    private final byte[] medium;
    private final byte[] large;
    private final String extension;
    private final String path;

    public Thumbnail(byte[] small, byte[] medium, byte[] large, String extension, String path) {
        this.small = small;
        this.medium = medium;
        this.large = large;
        this.extension = extension;
        this.path = path;
    }

    public byte[] getImage(String type) {
        if (type.equals("Small"))
            return small;
        
        if (type.equals("Medium"))
            return medium;

        if (type.equals("Big"))
            return large;
        
        return null;
    }

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }
}

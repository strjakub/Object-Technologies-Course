package model;

public class Thumbnail {

    private final Picture picture;
    private final byte[] small;
    private final byte[] medium;
    private final byte[] large;

    public Thumbnail(Picture picture, byte[] small, byte[] medium, byte[] large) {
        this.picture = picture;
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public int getPictureId() {
        return picture.getId();
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
        return picture.getExtension();
    }

    public String getPath() {
        return picture.getPath();
    }

    public byte[] getOriginal() {
        return picture.getData();
    }
}

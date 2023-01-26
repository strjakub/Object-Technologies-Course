package model;

public class Thumbnail {

    private final Picture picture;
    private final byte[] small;
    private final byte[] medium;
    private final byte[] large;
    private final boolean complete;

    public Thumbnail(Picture picture) {
        this.picture = picture;
        small = null;
        medium = null;
        large = null;
        complete = false;
    }

    public Thumbnail(Picture picture, byte[] small, byte[] medium, byte[] large) {
        this.picture = picture;
        this.small = small;
        this.medium = medium;
        this.large = large;
        complete = true;
    }
    
    public boolean isComplete() {
        return complete;
    }

    public Integer getPictureId() {
        return picture.getId();
    }

    public byte[] getPicture(PictureSizes size) {
        if (size == PictureSizes.Small)
            return small;
        
        if (size == PictureSizes.Medium)
            return medium;

        if (size == PictureSizes.Large)
            return large;
        
        throw new IllegalArgumentException(size.toString());
    }

    public byte[] getOriginal() {
        return picture.getData();
    }
}

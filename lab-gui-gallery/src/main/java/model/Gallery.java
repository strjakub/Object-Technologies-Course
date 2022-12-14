package model;

import java.util.ArrayList;
import java.util.List;

public class Gallery {

    private final List<Image> photos = new ArrayList<>();

    public void addPhoto(Image photo) {
        photos.add(photo);
    }

    public List<Image> getPhotos() {
        return photos;
    }

    public void clear() {
        photos.clear();
    }
}

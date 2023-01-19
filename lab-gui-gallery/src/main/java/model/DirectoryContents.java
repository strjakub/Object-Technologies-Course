package model;

public class DirectoryContents {
    
    private final Iterable<Thumbnail> thumbnails;
    private final Iterable<String> directories;

    public DirectoryContents(Iterable<Thumbnail> thumbnails, Iterable<String> directories) {
        this.thumbnails = thumbnails;
        this.directories = directories;
    }

    public Iterable<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public Iterable<String> getDirectories() {
        return directories;
    }
}

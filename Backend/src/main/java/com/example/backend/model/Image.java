package com.example.backend.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Image.TABLE_NAME)
public class Image {

    public static final String TABLE_NAME = "image";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = Columns.ID)
    private int id;

    @Column(name = Columns.CONTENT)
    private byte[] data;

    @Column(name = Columns.EXTENSION, nullable = false)
    private String extension;

    @OneToMany(mappedBy="image", cascade = CascadeType.ALL)
    private Set<Thumbnail> thumbnails;

    public Image() {}

    public int getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public String getExtension() {
        return extension;
    }

    public Set<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    public Image(byte[] data, String extension) {
        this.data = data;
        this.extension = extension;
    }

    public static class Columns {
        public static final String ID = "id";
        public static final String CONTENT = "content";
        public static final String EXTENSION = "extension";
    }

    @Override
    public String toString() {
        return "{id: " + id + ", data: " + Arrays.toString(data) + ", extension: " + extension + "}";
    }

}

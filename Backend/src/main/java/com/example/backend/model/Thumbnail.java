package com.example.backend.model;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = Thumbnail.TABLE_NAME)
public class Thumbnail {

    public static final String TABLE_NAME = "thumbnail";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = Columns.ID)
    private int id;

    @Column(name = Columns.CONTENT)
    private byte[] data;

    @Column(name = Columns.EXTENSION, nullable = false)
    private String extension;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "imageId")
    private Image image;

    public Thumbnail() {}

    public int getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public String getExtension() {
        return extension;
    }

    public Image getImage() {
        return image;
    }

    public Thumbnail(byte[] data, String extension, Image image) {
        this.data = data;
        this.extension = extension;
        this.image = image;
    }

    public static class Columns {
        public static final String ID = "id";
        public static final String CONTENT = "content";
        public static final String EXTENSION = "extension";
    }

    @Override
    public String toString() {
        return "{id: " + id + ", data: " + Arrays.toString(data) + ", extension: " + extension + ", imageId: " + image.getId() + "}";
    }

}

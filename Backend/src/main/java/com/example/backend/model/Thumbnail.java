package com.example.backend.model;

import com.example.backend.service.ByteArraySerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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

    @Lob
    @Column(name = Columns.SMALL)
    @JsonSerialize(using = ByteArraySerializer.class)
    private byte[] small;

    @Lob
    @Column(name = Columns.MEDIUM)
    @JsonSerialize(using = ByteArraySerializer.class)
    private byte[] medium;

    @Lob
    @Column(name = Columns.LARGE)
    @JsonSerialize(using = ByteArraySerializer.class)
    private byte[] large;


    @Column(name = Columns.EXTENSION, nullable = false)
    private String extension;

    @Column(name = Columns.PATH, nullable = false)
    private String path;

    @OneToOne
    @JoinColumn(name = "imageId")
    @JsonIgnore
    private Image image;

    public Thumbnail() {}

    public Thumbnail(byte[] small, byte[] medium, byte[] large, String extension, String path, Image image) {
        this.small = small;
        this.medium = medium;
        this.large = large;
        this.extension = extension;
        this.image = image;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public byte[] getSmall() {
        return small;
    }

    public byte[] getMedium() {
        return medium;
    }

    public byte[] getLarge() {
        return large;
    }

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }

    public Image getImage() {
        return image;
    }

    public static class Columns {
        public static final String ID = "id";
        public static final String SMALL = "small thumbnail";
        public static final String MEDIUM = "medium thumbnail";
        public static final String LARGE = "large thumbnail";
        public static final String EXTENSION = "extension";
        public static final String PATH = "path";
    }

    @Override
    public String toString() {
        return "{id: " + id + ", small: " + Arrays.toString(small) + ", extension: " + extension + ", imageId: " + image.getId() + "}";
    }

}

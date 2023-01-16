package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.jackson.JsonComponent;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = Thumbnail.TABLE_NAME)
@JsonComponent
public class Thumbnail {

    public static final String TABLE_NAME = "thumbnail";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = Columns.ID)
    private int id;

    @Lob
    @Column(name = Columns.SMALL)
    private byte[] small;

    @Lob
    @Column(name = Columns.MEDIUM)
    private byte[] medium;

    @Lob
    @Column(name = Columns.LARGE)
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
        this.path = path;
        this.image = image;
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

    public void setSmall(byte[] small) {
        this.small = small;
    }

    public void setMedium(byte[] medium) {
        this.medium = medium;
    }

    public void setLarge(byte[] large) {
        this.large = large;
    }

    public static class Columns {
        public static final String ID = "id";
        public static final String SMALL = "small";
        public static final String MEDIUM = "medium";
        public static final String LARGE = "large";
        public static final String EXTENSION = "extension";
        public static final String PATH = "path";
    }

    @Override
    public String toString() {
        return "{id: " + id + ", small: " + Arrays.toString(small) + ", extension: " + extension + ", imageId: " + image.getId() + "}";
    }

}

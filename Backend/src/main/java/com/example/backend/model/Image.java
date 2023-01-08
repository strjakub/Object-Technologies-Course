package com.example.backend.model;

import com.example.backend.service.ByteArraySerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = Image.TABLE_NAME)
public class Image {

    public static final String TABLE_NAME = "image";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = Columns.ID)
    private int id;

    @Lob
    @Column(name = Columns.CONTENT)
    @JsonSerialize(using = ByteArraySerializer.class)
    private byte[] data;

    @Column(name = Columns.EXTENSION, nullable = false)
    private String extension;

    @Column(name = Columns.PATH, nullable = false)
    private String path;

    @JsonIgnore
    @OneToOne(mappedBy="image")
    private Thumbnail thumbnail;

    public Image() {}

    public Image(byte[] data, String extension, String path) {
        this.data = data;
        this.extension = extension;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public String getExtension() {
        return extension;
    }

    public String getPath() {
        return path;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public static class Columns {
        public static final String ID = "id";
        public static final String CONTENT = "content";
        public static final String EXTENSION = "extension";
        public static final String PATH = "path";
    }

    @Override
    public String toString() {
        return "{id: " + id + ", data: " + Arrays.toString(data) + ", extension: " + extension + ", path: " + path + "}";
    }

}

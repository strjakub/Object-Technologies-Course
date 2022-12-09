package com.example.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Thumbnail {

    @Id
    @GeneratedValue
    private int id;

    private byte[] image;

    private String extension;

    public Thumbnail() {
    }

    public Thumbnail(int id, byte[] image, String extension) {
        this.id = id;
        this.image = image;
        this.extension = extension;
    }

    public int getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public String getExtension() {
        return extension;
    }
}

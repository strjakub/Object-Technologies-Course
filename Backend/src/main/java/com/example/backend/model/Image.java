package com.example.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Image {

    @Id
    @GeneratedValue
    private int id;

    private byte[] thumbnail;

    public Image() {
    }

    public Image(int id, byte[] thumbnail) {
        this.id = id;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }
}

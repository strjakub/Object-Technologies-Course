package com.example.backend.model;

import org.springframework.boot.jackson.JsonComponent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Directory.TABLE_NAME)
@JsonComponent
public class Directory {

    public static final String TABLE_NAME = "directory";

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = Columns.ID)
    private int id;

    @Column(name = Columns.PATH)
    private String path;

    public Directory(){}

    public Directory(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static class Columns {
        public static final String ID = "id";
        public static final String PATH = "path";
    }

}

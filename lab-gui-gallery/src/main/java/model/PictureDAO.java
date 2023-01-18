package model;

import java.util.Base64;

public class PictureDAO {

    private int id;
    private String data;
    private String extension;
    private String path;
    
    public int getId() {
        return id;
    }

    public static Picture convertTo(PictureDAO dto) {
        var data = Base64.getDecoder().decode(dto.data);
        return new Picture(data, dto.extension, dto.path);
    }
}

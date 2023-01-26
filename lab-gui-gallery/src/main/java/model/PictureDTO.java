package model;

import java.util.Base64;

public class PictureDTO {

    private int id;
    private String data;
    private String extension;
    private String path;
    
    public static Picture convertTo(PictureDTO dto) {
        var data = Base64.getDecoder().decode(dto.data);
        return new Picture(dto.id, data, dto.extension, dto.path);
    }
}

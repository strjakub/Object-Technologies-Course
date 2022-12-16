package model;

import java.util.Base64;

public class PictureDAO {
    private String data;
    private String extension;
    
    public static Picture convertTo(PictureDAO dto) {
        return new Picture(Base64.getDecoder().decode(dto.data), dto.extension);
    }
}

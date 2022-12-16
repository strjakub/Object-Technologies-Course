package model;

import java.util.Base64;

public class Dto {
    private String data;
    private String extension;
    
    public static Image convertTo(Dto dto) {
        return new Image(Base64.getDecoder().decode(dto.data), dto.extension);
    }
}

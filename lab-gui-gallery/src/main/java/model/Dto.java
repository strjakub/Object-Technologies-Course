package model;

import java.util.Base64;

public class Dto {
    public Integer id;
    public String data;
    public String extension;
    
    public static Image convertTo(Dto dto) {
        return new Image(Base64.getDecoder().decode(dto.data), dto.extension);
    }
}

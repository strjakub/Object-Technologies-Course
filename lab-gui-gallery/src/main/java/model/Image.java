package model;

import java.util.Base64;

public class Image {
    
    private final byte[] data;
    private final String extension;

    public Image(byte[] data, String extension) {
        this.data = data;
        this.extension = extension;
    }

    public byte[] getData() {
        return this.data;
    }

    public static Image fromDto(Dto dto) {
        return new Image(Base64.getDecoder().decode(dto.data), dto.extension);
    }
}

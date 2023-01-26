package model;

import java.util.Base64;

public class ThumbnailDTO {
    
    private String small;
    private String medium;
    private String large;
    private boolean complete;
    private PictureDTO image;

    public static Thumbnail convertTo(ThumbnailDTO dto) {
        var picture = PictureDTO.convertTo(dto.image);

        if (!dto.complete) {
            return new Thumbnail(picture);
        }

        var sm = Base64.getDecoder().decode(dto.small);
        var md = Base64.getDecoder().decode(dto.medium);
        var lg = Base64.getDecoder().decode(dto.large);
        return new Thumbnail(picture, sm, md, lg);
    }

}

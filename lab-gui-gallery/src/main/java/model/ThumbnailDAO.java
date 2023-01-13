package model;

import java.util.Base64;

public class ThumbnailDAO {
    
    private String small;
    private String medium;
    private String large;
    private String extension;
    private String path;

    public static Thumbnail convertTo(ThumbnailDAO dto) {
        var sm = Base64.getDecoder().decode(dto.small);
        var md = Base64.getDecoder().decode(dto.medium);
        var lg = Base64.getDecoder().decode(dto.large);
        return new Thumbnail(sm, md, lg, dto.extension, dto.path);
    }

}

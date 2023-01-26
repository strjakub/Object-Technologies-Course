package model;

import java.util.Base64;

public class ThumbnailDAO {
    
    private String small;
    private String medium;
    private String large;
    private boolean complete;
    private PictureDAO image;

    public static Thumbnail convertTo(ThumbnailDAO dao) {
        var picture = PictureDAO.convertTo(dao.image);

        if (!dao.complete) {
            return new Thumbnail(picture);
        }

        var sm = Base64.getDecoder().decode(dao.small);
        var md = Base64.getDecoder().decode(dao.medium);
        var lg = Base64.getDecoder().decode(dao.large);
        return new Thumbnail(picture, sm, md, lg);
    }

}

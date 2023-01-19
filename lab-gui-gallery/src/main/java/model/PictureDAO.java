package model;

import java.util.Base64;

public class PictureDAO {

    private int id;
    private String data;
    private String extension;
    private String path;
    
    public static Picture convertTo(PictureDAO dao) {
        var data = Base64.getDecoder().decode(dao.data);
        return new Picture(dao.id, data, dao.extension, dao.path);
    }
}

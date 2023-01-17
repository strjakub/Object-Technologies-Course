package services;

import java.util.Collection;

import model.Picture;
import model.PictureDAO;
import model.ThumbnailDAO;

public interface IRetrofitService {
    void postImage(Picture image, NetworkCallback<Integer> callback);
    void getImage(Integer id, NetworkCallback<PictureDAO> callback);
    void getThumbnail(Integer id, NetworkCallback<ThumbnailDAO> callback);
    void getThumbnails(String path, NetworkCallback<Collection<ThumbnailDAO>> callback);
    void cancelAll();
}

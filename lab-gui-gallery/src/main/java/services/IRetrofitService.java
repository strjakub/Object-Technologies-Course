package services;

import model.Picture;
import model.PictureDAO;

public interface IRetrofitService {
    void postImage(Picture image, NetworkCallback<Integer> callback);
    void getImage(Integer id, NetworkCallback<PictureDAO> callback);
    void getThumbnail(Integer id, NetworkCallback<PictureDAO> callback);
}

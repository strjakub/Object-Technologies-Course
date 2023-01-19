package services;

import model.DirectoryContentsDAO;
import model.Picture;
import model.ThumbnailDAO;

public interface IRetrofitService {
    void postImage(Picture image, NetworkCallback<Integer> callback);
    void getThumbnail(Integer id, NetworkCallback<ThumbnailDAO> callback);
    void getPathContents(String path, NetworkCallback<DirectoryContentsDAO> callback);
    void cancelAll();
}

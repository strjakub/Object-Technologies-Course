package services;

import model.DirectoryContentsDAO;
import model.Picture;
import model.ThumbnailDTO;

public interface IRetrofitService {
    void postPicture(Picture image, NetworkCallback<Integer> callback);
    void getThumbnail(Integer id, NetworkCallback<ThumbnailDTO> callback);
    void getPathContents(String path, NetworkCallback<DirectoryContentsDAO> callback);
    void postDirectory(String path, NetworkCallback<Integer> callback);
    void cancelAll();
}

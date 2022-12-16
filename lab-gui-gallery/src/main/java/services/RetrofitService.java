package services;

import model.PictureDAO;
import model.Picture;

public class RetrofitService {

    private static RetrofitInterface getApInterface() {
        return RetrofitClient.getClient().create(RetrofitInterface.class);
    }

    public static void postImage(Picture image, NetworkCallback<Integer> callback) {        
        getApInterface().postImage(image).enqueue(callback);
    }

    public static void getImage(Integer id, NetworkCallback<PictureDAO> callback) {        
        getApInterface().getImage(id).enqueue(callback);
    }

    public static void getThumbnail(Integer id, NetworkCallback<PictureDAO> callback) {        
        getApInterface().getThumbnail(id).enqueue(callback);
    }
}


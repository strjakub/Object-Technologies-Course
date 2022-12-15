package services;

import model.Image;

public class RetrofitService {

    private static RetrofitInterface getApInterface() {
        return RetrofitClient.getClient().create(RetrofitInterface.class);
    }

    public static void postImage(Image image, NetworkCallback<Integer> callback) {        
        getApInterface().postImage(image).enqueue(callback);
    }

    public static void getImage(Integer id, NetworkCallback<Image> callback) {        
        getApInterface().getImage(id).enqueue(callback);
    }

    public static void getThumbnail(Integer id, NetworkCallback<Image> callback) {        
        getApInterface().getThumbnail(id).enqueue(callback);
    }
}


package services;

import model.Image;

public class RetrofitService {

    public static void postImage(Image image, NetworkCallback<Integer> callback) {        
        var apiInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        var call = apiInterface.postImage(image);
        call.enqueue(callback);
    }

    public static void getImage(Integer id, NetworkCallback<Image> callback) {        
        var apiInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        var call = apiInterface.getImage(id);
        call.enqueue(callback);
    }

    public static void getThumbnail(Integer id, NetworkCallback<Image> callback) {        
        var apiInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        var call = apiInterface.getThumbnail(id);
        call.enqueue(callback);
    }
}


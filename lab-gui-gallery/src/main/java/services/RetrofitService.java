package services;

import model.Image;

public class RetrofitService {

    public static void postImage(Image image, NetworkCallback<Integer> callback) {        
        var apiInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        var call = apiInterface.postImage(image);
        call.enqueue(callback);
    }
}


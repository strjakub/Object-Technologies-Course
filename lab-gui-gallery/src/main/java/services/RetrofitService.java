package services;

import model.Picture;
import model.PictureDAO;

public class RetrofitService implements IRetrofitService {

    public static final IRetrofitService Instance = new RetrofitService(RetrofitClient.Instance);

    private final IRetrofitClient retrofitClient;

    public RetrofitService(IRetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
    }

    private RetrofitInterface getApInterface() {
        return retrofitClient.getClient().create(RetrofitInterface.class);
    }

    public void postImage(Picture image, NetworkCallback<Integer> callback) {        
        getApInterface().postImage(image).enqueue(callback);
    }

    public void getImage(Integer id, NetworkCallback<PictureDAO> callback) {        
        getApInterface().getImage(id).enqueue(callback);
    }

    public void getThumbnail(Integer id, NetworkCallback<PictureDAO> callback) {        
        getApInterface().getThumbnail(id).enqueue(callback);
    }
}


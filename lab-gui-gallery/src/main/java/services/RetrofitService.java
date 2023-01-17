package services;

import model.Picture;
import model.PictureDAO;
import model.ThumbnailDAO;

public class RetrofitService implements IRetrofitService {

    private final IRetrofitClient retrofitClient;

    private final RetryPolicy retryPolicy = RetryPolicyBuilder
        .handleFailure(true)
        .orResult(HttpStatusCode.InternalServerError)
        .orResult(HttpStatusCode.RequestTimeout)
        .waitAndRetry(Backoff.DecorrelatedJitter(1000, 6))
        .repreatProcessing(true)
        .build();

    public RetrofitService(IRetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
    }

    private RetrofitInterface getApInterface() {
        return retrofitClient.getClient().create(RetrofitInterface.class);
    }

    public void postImage(Picture image, NetworkCallback<Integer> callback) {
        retryPolicy.execute(getApInterface().postImage(image), callback);
    }

    public void getImage(Integer id, NetworkCallback<PictureDAO> callback) {        
        retryPolicy.execute(getApInterface().getImage(id), callback);
    }

    public void getThumbnail(Integer id, NetworkCallback<ThumbnailDAO> callback) {       
        retryPolicy.execute(getApInterface().getThumbnail(id), callback);
    }
}


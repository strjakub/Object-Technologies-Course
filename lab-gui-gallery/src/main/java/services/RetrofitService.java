package services;

import model.Picture;
import model.PictureDAO;
import model.ThumbnailDAO;

public class RetrofitService implements IRetrofitService {

    private final IRetrofitClient retrofitClient;

    public RetrofitService(IRetrofitClient retrofitClient) {
        this.retrofitClient = retrofitClient;
    }

    private RetrofitInterface getApInterface() {
        return retrofitClient.getClient().create(RetrofitInterface.class);
    }

    public void postImage(Picture image, NetworkCallback<Integer> callback) {

        var retryPolicy = RetryPolicyBuilder
            .handleFailure(true)
            .orResult(HttpStatusCode.InternalServerError)
            .waitAndRetry(Backoff.DecorrelatedJitter(1000, 6))
            .build();

        retryPolicy.execute(getApInterface().postImage(image), callback);
    }

    public void getImage(Integer id, NetworkCallback<PictureDAO> callback) {        

        var retryPolicy = RetryPolicyBuilder
            .handleFailure(true)
            .orResult(HttpStatusCode.InternalServerError)
            .repreatProcessing(true)
            .build();
        
        retryPolicy.execute(getApInterface().getImage(id), callback);
    }

    public void getThumbnail(Integer id, NetworkCallback<ThumbnailDAO> callback) {       
        
        var retryPolicy = RetryPolicyBuilder
            .handleFailure(true)
            .orResult(HttpStatusCode.InternalServerError)
            .build();
    
        retryPolicy.execute(getApInterface().getThumbnail(id), callback);
    }
}


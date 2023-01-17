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
            .<Integer>HandleFailure(true)
            .OrResult(HttpStatusCode.InternalServerError)
            .WaitAndRetry(Backoff.DecorrelatedJitter(1000, 6))
            .Build();

        retryPolicy.ExecuteAsync(getApInterface().postImage(image), callback);
    }

    public void getImage(Integer id, NetworkCallback<PictureDAO> callback) {        

        var retryPolicy = RetryPolicyBuilder
            .<PictureDAO>HandleFailure(true)
            .OrResult(HttpStatusCode.InternalServerError)
            .RepreatProcessing(true)
            .Build();
        
        retryPolicy.ExecuteAsync(getApInterface().getImage(id), callback);
    }

    public void getThumbnail(Integer id, NetworkCallback<ThumbnailDAO> callback) {       
        
        var retryPolicy = RetryPolicyBuilder
            .<ThumbnailDAO>HandleFailure(true)
            .OrResult(HttpStatusCode.InternalServerError)
            .RepreatProcessing(true)
            .Build();
    
        retryPolicy.ExecuteAsync(getApInterface().getThumbnail(id), callback);
    }
}


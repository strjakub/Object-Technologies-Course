package services;

import model.DirectoryContentsDAO;
import model.Picture;
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
    
    @Override
    public void postPicture(Picture image, NetworkCallback<Integer> callback) {
        retryPolicy.execute(getApInterface().postPicture(image), callback);
    }

    @Override
    public void getThumbnail(Integer id, NetworkCallback<ThumbnailDAO> callback) {       
        retryPolicy.execute(getApInterface().getThumbnail(id), callback);
    }

    @Override
    public void getPathContents(String path, NetworkCallback<DirectoryContentsDAO> callback) {
        retryPolicy.execute(getApInterface().getPathContents(path), callback);
    }

    @Override
    public void cancelAll() {
        retrofitClient.cancelAll();
    }
}


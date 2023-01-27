package services;

import model.DirectoryContentsDAO;
import model.DirectoryDTO;
import model.Picture;
import model.ThumbnailDTO;

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
    public void getThumbnail(Integer id, NetworkCallback<ThumbnailDTO> callback) {       
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

    @Override
    public void postDirectory(String path, NetworkCallback<Integer> callback) {
        var dto = new DirectoryDTO(path);
        retryPolicy.execute(getApInterface().postDirectory(dto), callback);        
    }
}


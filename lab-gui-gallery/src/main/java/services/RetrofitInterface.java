package services;

import model.Image;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("/")
    Call<Integer> postImage(@Body Image image);
}

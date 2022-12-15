package services;

import model.Dto;
import model.Image;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("/")
    Call<Integer> postImage(@Body Image image);

    @GET("{id}")
    Call<Dto> getImage(@Path("id") Integer id);

    @GET("thumbnail/{id}")
    Call<Dto> getThumbnail(@Path("id") Integer id);
}

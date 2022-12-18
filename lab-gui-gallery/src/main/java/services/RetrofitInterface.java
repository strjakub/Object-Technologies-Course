package services;

import model.Picture;
import model.PictureDAO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("/")
    Call<Integer> postImage(@Body Picture image);

    @GET("{id}")
    Call<PictureDAO> getImage(@Path("id") Integer id);

    @GET("thumbnail/{id}")
    Call<PictureDAO> getThumbnail(@Path("id") Integer id);
}

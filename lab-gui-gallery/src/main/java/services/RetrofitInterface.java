package services;

import model.DirectoryContentsDAO;
import model.Picture;
import model.PictureDAO;
import model.ThumbnailDAO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/")
    Call<Integer> postImage(@Body Picture image);

    @GET("{id}")
    Call<PictureDAO> getImage(@Path("id") Integer id);

    @GET("thumbnail/{id}")
    Call<ThumbnailDAO> getThumbnail(@Path("id") Integer id);

    @GET("path")
    Call<DirectoryContentsDAO> getPathContents(@Query("path") String path);
}

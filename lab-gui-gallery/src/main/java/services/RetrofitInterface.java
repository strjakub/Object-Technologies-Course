package services;

import model.DirectoryContentsDAO;
import model.DirectoryDTO;
import model.Picture;
import model.ThumbnailDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/")
    Call<Integer> postPicture(@Body Picture image);

    @POST("directory")
    Call<Integer> postDirectory(@Body DirectoryDTO directory);

    @GET("thumbnail/{id}")
    Call<ThumbnailDTO> getThumbnail(@Path("id") Integer id);

    @GET("path")
    Call<DirectoryContentsDAO> getPathContents(@Query("path") String path);
}

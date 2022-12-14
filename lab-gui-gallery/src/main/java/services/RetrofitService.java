package services;

import model.Image;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitService {

    public static void postImage(Image image) {
        
        var apiInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        var call = apiInterface.postImage(image);
        
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                var d = response.body();
                System.out.println(d);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                call.cancel();
            }
        });

    }
}

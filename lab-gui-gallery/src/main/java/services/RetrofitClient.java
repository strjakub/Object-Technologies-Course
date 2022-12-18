package services;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient implements IRetrofitClient  {

    public static final IRetrofitClient Instance = new RetrofitClient();

    private static final int TIMEOUT = 60;
    private static final String BASE_URL ="http://localhost:8080/";
    
    private Retrofit retrofit;

    public Retrofit getClient() {

        if (retrofit == null) {

            var okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        
        return retrofit;
    }
}

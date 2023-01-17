package services;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient implements IRetrofitClient  {

    private static final int TIMEOUT = 60;
    private static final String BASE_URL ="http://localhost:8080/";
    
    private Retrofit retrofit;
    private OkHttpClient client;

    public Retrofit getClient() {

        if (retrofit == null) {

            client = new OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();
        }
    
        return retrofit;
    }

    public void cancelAll() {
        if (client != null) {
            client.dispatcher().cancelAll();
        }
    }
}

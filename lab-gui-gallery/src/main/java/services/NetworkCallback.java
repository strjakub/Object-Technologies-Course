package services;

import java.io.IOException;

import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCallback<T> implements Callback<T> {

    public void process(T result) throws IOException {}

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response == null) {
            call.cancel();
            System.out.println("Error while http request");
        }

        var result = response.body();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    process(result);
                }
                catch (IOException e) {
                    e.getStackTrace();
                }
            }
        });

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        call.cancel();
        System.out.println("Error while http request");
    }
}

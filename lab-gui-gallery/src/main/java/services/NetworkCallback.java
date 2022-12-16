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

        if (response.code() == 102) {
            System.out.println("Got status code nr. 102");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            call.clone().enqueue(this);
            return;
        }
        if (response == null || response.code() == 500 || response.code() == 404) {
            call.cancel();
            System.out.println("Error while http request");
            System.out.println("response is null");
            return;
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
        System.out.println(t.getMessage());
        System.out.println(t.toString());
    }
}

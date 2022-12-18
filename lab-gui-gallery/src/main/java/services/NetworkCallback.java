package services;

import java.io.IOException;

import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCallback<T> implements Callback<T> {

    private static final int HTTP_PROCESSING = 102;
    private static final int DELAY_IN_MILISECONDS = 1000;

    public void process(T result) throws IOException {}

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response == null) {
            call.cancel();
            System.out.println("Error, http response is null");
            return;
        }

        var code = response.code();

        if (code == HTTP_PROCESSING) {
            try {
                Thread.sleep(DELAY_IN_MILISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            call.clone().enqueue(this);
            return;
        }

        if (400 <= code && code < 600) {
            call.cancel();
            System.out.println("Error while http request");
            return;
        }

        var result = response.body();
        Platform.runLater(() -> {
            try {
                process(result);
            }
            catch (IOException e) {
                e.getStackTrace();
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

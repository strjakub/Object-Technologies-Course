package services;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javafx.application.Platform;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkCallback<T> implements Callback<T> {

    private static final Logger logger = LogManager.getLogger(NetworkCallback.class);

    public abstract void process(T result) throws IOException;

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
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
        logger.debug("Error while http request");
        logger.debug(t.getMessage());
        logger.debug(t.toString());
    }
}

package services;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCallback<T> implements Callback<T> {

    private final T parameter;

    public NetworkCallback(T parameter) {
        this.parameter = parameter;
    }

    T getValue() {
        return this.parameter;
    }

    public void process(T result) {}

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        var result = response.body();
        process(result);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        call.cancel();
        System.out.println("Error while for" + parameter.toString());
    }
}

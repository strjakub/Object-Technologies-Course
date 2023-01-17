package services;

import retrofit2.Call;

public interface IRetryPolicy<T> {
    void ExecuteAsync(Call<T> call, NetworkCallback<T> callback);
}

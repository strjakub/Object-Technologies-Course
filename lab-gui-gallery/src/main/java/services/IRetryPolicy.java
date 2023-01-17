package services;

import retrofit2.Call;

public interface IRetryPolicy {
    <T> void execute(Call<T> call, NetworkCallback<T> callback);
}

package services;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetryPolicy<T> implements IRetryPolicy<T>, Callback<T> {

    private final RetryPolicyBuilder<T> policyBuilder;
    private NetworkCallback<T> callback;

    public RetryPolicy(RetryPolicyBuilder<T> policyBuilder) {
        this.policyBuilder = policyBuilder;
    }

    public void ExecuteAsync(Call<T> call, NetworkCallback<T> callback) {
        this.callback = callback;
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        var code = HttpStatusCode.getCode(response.code());


        if (code == HttpStatusCode.Success) {
            callback.onResponse(call, response);
            return;
        }

        if (code == HttpStatusCode.Processing && policyBuilder.shouldRepreatProcessing()) {
        
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        
            call.clone().enqueue(this);
            return;
        }

        if (!policyBuilder.shouldHandleCode(code)) {
            call.cancel();
            return;
        }

        if (nextTryHandled(call)) {
            return;
        }

        callback.onFailure(call, new IOException());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (!policyBuilder.shouldHandleFailure()) {
            call.cancel();
            return;
        }

        if (nextTryHandled(call)) {
            return;
        }

        callback.onFailure(call, t);
    }

    private boolean nextTryHandled(Call<T> call) {
        var iterator = policyBuilder.getTries();
        if (iterator.hasNext()) {
            var sleep = iterator.next();

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            call.clone().enqueue(this);

            return true;
        }

        return false;
    }
}

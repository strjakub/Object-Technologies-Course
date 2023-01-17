package services;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RetryCallback<T> implements Callback<T> {

    private final RetryPolicyBuilder policyBuilder;
    private final NetworkCallback<T> callback;

    public RetryCallback(RetryPolicyBuilder policyBuilder, NetworkCallback<T> callback) {
        this.policyBuilder = policyBuilder;
        this.callback = callback;
    }

    public void execute(Call<T> call) {
        call.enqueue(this);
    }

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

package services;

import retrofit2.Call;

public class RetryPolicy {

    private final RetryPolicyBuilder policyBuilder;

    public RetryPolicy(RetryPolicyBuilder policyBuilder) {
        this.policyBuilder = policyBuilder;
    }

    public <T> void execute(Call<T> call, NetworkCallback<T> callback) {
        new RetryCallback<T>(policyBuilder, callback).execute(call);
    }
}

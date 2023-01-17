package services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RetryPolicyBuilder<T> {
    
    private final boolean handleFailure;
    private final List<Integer> httpStatusCodes = new ArrayList<Integer>();
    private Iterator<Integer> tries;
    private boolean repreatProcessing;

    private RetryPolicyBuilder(boolean handleFailure) {
        this.handleFailure = handleFailure;
    }

    public static <T> RetryPolicyBuilder<T> HandleFailure(boolean handleFailure) {
        return new RetryPolicyBuilder<T>(handleFailure);
    }

    public RetryPolicyBuilder<T> OrResult(int statusCode) {
        httpStatusCodes.add(statusCode);
        return this;
    }

    public RetryPolicyBuilder<T> WaitAndRetry(Iterator<Integer> tries) {
        this.tries = tries;
        return this;
    }

    public RetryPolicyBuilder<T> RepreatProcessing(boolean repreat) {
        this.repreatProcessing = repreat;
        return this;
    }

    public boolean shouldHandleFailure() {
        return handleFailure;
    }

    public boolean shouldRepreatProcessing() {
        return repreatProcessing;
    }

    public Iterator<Integer> getTries() {
        return tries;
    }

    public boolean shouldHandleCode(int httpStatusCode) {
        return httpStatusCodes.contains(httpStatusCode);
    }

    public RetryPolicy<T> Build() {
        return new RetryPolicy<T>(this);
    }

}

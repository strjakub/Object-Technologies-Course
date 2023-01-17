package services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RetryPolicyBuilder {
    
    private final boolean handleFailure;
    private final List<HttpStatusCode> httpStatusCodes = new ArrayList<HttpStatusCode>();
    private Iterator<Integer> tries;
    private boolean repreatProcessing;

    private RetryPolicyBuilder(boolean handleFailure) {
        this.handleFailure = handleFailure;
    }

    public static RetryPolicyBuilder handleFailure(boolean handleFailure) {
        return new RetryPolicyBuilder(handleFailure);
    }

    public RetryPolicyBuilder orResult(HttpStatusCode statusCode) {
        httpStatusCodes.add(statusCode);
        return this;
    }

    public RetryPolicyBuilder waitAndRetry(Iterator<Integer> tries) {
        this.tries = tries;
        return this;
    }

    public RetryPolicyBuilder repreatProcessing(boolean repreat) {
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

    public boolean shouldHandleCode(HttpStatusCode httpStatusCode) {
        return httpStatusCodes.contains(httpStatusCode);
    }

    public RetryPolicy build() {
        return new RetryPolicy(this);
    }

}

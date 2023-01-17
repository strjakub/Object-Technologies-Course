package services;

public enum HttpStatusCode {
    Processing(102),
    Success(200),
    InternalServerError(500);

    private final int value;

    private HttpStatusCode(int value) {
        this.value = value;
    }

    public static HttpStatusCode getCode(int code) {
        if (code == Processing.value) 
            return Processing;

        if (code == Success.value) 
            return Success;
        
        if (code == InternalServerError.value) 
            return InternalServerError;

        throw new IllegalArgumentException(String.format("%s: unknown http code", code));
    }
}

package pl.daffit.sklepmc.api;

public class ApiException extends RuntimeException {

    private ApiError apiError;

    public ApiException(String path, ApiError apiError) {
        this.apiError = apiError;
    }

    public ApiException(ApiError apiError, String s) {
        super(s);
        this.apiError = apiError;
    }

    public ApiException(ApiError apiError, String s, Throwable throwable) {
        super(s, throwable);
        this.apiError = apiError;
    }

    public ApiException(ApiError apiError, Throwable throwable) {
        super(throwable);
        this.apiError = apiError;
    }

    public ApiException(ApiError apiError, String s, Throwable throwable, boolean b1, boolean b2) {
        super(s, throwable, b1, b2);
        this.apiError = apiError;
    }

    public ApiError getApiError() {
        return this.apiError;
    }
}

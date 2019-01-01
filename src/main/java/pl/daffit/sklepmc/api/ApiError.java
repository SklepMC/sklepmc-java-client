package pl.daffit.sklepmc.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiError {

    private final String type;
    private final String message;

    @JsonCreator()
    public ApiError(@JsonProperty("type") String type, @JsonProperty("message") String message) {
        this.type = type;
        this.message = message;
    }

    @JsonProperty("error")
    public boolean isError() {
        return true;
    }

    @JsonProperty("type")
    public String getType() {
        return this.type;
    }

    @JsonProperty("message")
    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiError.class.getSimpleName() + "[", "]")
                .add("type='" + this.type + "'")
                .add("message='" + this.message + "'")
                .toString();
    }

    public enum ErrorType {
        UNKNOWN,
        PARSE_ERROR,
        AUTHORIZATION_FAILED,
        ELEMENT_NOT_FOUND,
        INVALID_INPUT
    }
}

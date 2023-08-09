package com.rks.mcommon.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError() {
        super();
    }

    public ApiError(final HttpStatus status, final String message, final List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(final HttpStatus status, final String message, final String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", errors=" + errors +
                '}';
    }
}

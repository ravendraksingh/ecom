package com.rks.gateway.exceptions;

public class CustomExceptionInfo {

    public CustomExceptionInfo(int status, String error_type, String error_code, String message) {
        this.status = status;
        this.error_type = error_type;
        this.error_code = error_code;
        this.message = message;
    }

    private int status;
    private String error_type;
    private String error_code;
    private String message;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError_type() {
        return error_type;
    }

    public void setError_type(String error_type) {
        this.error_type = error_type;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

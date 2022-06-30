package io.rks.apigateway.exceptions;

import org.json.JSONObject;

public class ApiException extends RuntimeException {
    private int status;
    private String error;
    private String message;

    public ApiException(int status, String error, String message) {
        super(message);
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("error", error);
        jsonObject.put("message", message);
        return jsonObject.toString();
    }
}

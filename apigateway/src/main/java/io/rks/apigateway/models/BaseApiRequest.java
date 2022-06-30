package io.rks.apigateway.models;

import java.sql.Timestamp;

public class BaseApiRequest {
    private String clientId;
    private String keyId;
    private String reqDate;
    private String rksTimestamp;
    private String authorization;
    private String requestIp;
    private Timestamp createdOn;
    private String requestMethod;
    private String requestUri;
    private String apiUri;
    private String requestBody;
    private String responseBody;
    private Timestamp processedOn;
    private String contentType;
    private String acceptType;
    private String serverAuthorization;
    private String traceId;
    private String authMethod;
    private String algorithm;
    private String signature;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getRksTimestamp() {
        return rksTimestamp;
    }

    public void setRksTimestamp(String rksTimestamp) {
        this.rksTimestamp = rksTimestamp;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getApiUri() {
        return apiUri;
    }

    public void setApiUri(String apiUri) {
        this.apiUri = apiUri;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public Timestamp getProcessedOn() {
        return processedOn;
    }

    public void setProcessedOn(Timestamp processedOn) {
        this.processedOn = processedOn;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAcceptType() {
        return acceptType;
    }

    public void setAcceptType(String acceptType) {
        this.acceptType = acceptType;
    }

    public String getServerAuthorization() {
        return serverAuthorization;
    }

    public void setServerAuthorization(String serverAuthorization) {
        this.serverAuthorization = serverAuthorization;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public BaseApiRequest() {
    }
}



package io.rks.apigateway.models;

import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import io.rks.apigateway.constants.HttpConstants;
import io.rks.apigateway.exceptions.ApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static io.rks.apigateway.constants.ExceptionConstants.AUTHORIZATION_HEADER_INVALID;

public class BaseApiRequestBuilder {
    static Logger logger = LogManager.getLogger(BaseApiRequestBuilder.class);

    public static BaseApiRequest build(HttpServletRequest request) {
        try {
            String method = request.getMethod();
            String requestIp = request.getRemoteAddr();
            String resourcePath = request.getRequestURI();
            String queryStr = request.getQueryString();
            if (queryStr != null && queryStr.length() >0) {
                resourcePath = resourcePath + "?" + queryStr;
            }
            String traceId = request.getHeader(HttpConstants.HEADER_TRACEID);
            String authorization = request.getHeader(HttpConstants.HEADER_AUTHORIZATION);
            String timestamp = request.getHeader(HttpConstants.HEADER_TIMESTAMP);
            String acceptType = request.getHeader(HttpConstants.HEADER_CONTENT_ACCEPT);
            String requestBody = null;
            String requestBodyToLog = null;

            if (HttpConstants.METHOD_POST.equalsIgnoreCase(method)
            || HttpConstants.METHOD_PUT.equalsIgnoreCase(method)
            || HttpConstants.METHOD_DELETE.equalsIgnoreCase(method)) {
                try (final InputStream requestDataStream = request.getInputStream()) {
                    requestBody = CharStreams.toString(new InputStreamReader(requestDataStream, StandardCharsets.UTF_8));
                }
            }
            String mediaType = request.getHeader(HttpConstants.HEADER_CONTENT_TYPE);
            BaseApiRequest authRequest = new BaseApiRequest();
            authRequest.setTraceId(traceId);
            authRequest.setAuthorization(authorization);
            authRequest.setRksTimestamp(timestamp);
            authRequest.setRequestIp(requestIp);
            authRequest.setRequestMethod(method.toUpperCase());
            authRequest.setRequestUri(resourcePath);
            authRequest.setApiUri((String) request.getAttribute("apiUri"));
            authRequest.setRequestBody(requestBody);
            authRequest.setContentType(mediaType);
            authRequest.setAcceptType(acceptType);

            if (!Strings.isNullOrEmpty(authorization)) {
                //TODO authorization parsing
            }
            return authRequest;
        } catch (ApiException ae) {
            ae.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            ApiException ape = new ApiException(401, "auth_error", AUTHORIZATION_HEADER_INVALID);
            throw ape;
        }
        return null;
    }
}

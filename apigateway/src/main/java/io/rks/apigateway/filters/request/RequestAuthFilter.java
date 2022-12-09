package io.rks.apigateway.filters.request;

import com.netflix.zuul.context.RequestContext;
import io.rks.apigateway.auth.JWTAuthenticator;
import io.rks.apigateway.constants.HttpConstants;
import io.rks.apigateway.exceptions.ApiException;
import io.rks.apigateway.exceptions.ApiExceptionFactory;
import io.rks.apigateway.exceptions.ApiUnauthorizedException;
import io.rks.apigateway.models.AuthResult;
import io.rks.apigateway.models.BaseApiRequest;
import io.rks.apigateway.models.BaseApiRequestBuilder;
import io.rks.apigateway.utils.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.rks.apigateway.constants.ExceptionConstants.*;

@Component
public class RequestAuthFilter {
    Logger logger = LogManager.getLogger(RequestAuthFilter.class);

    @Autowired
    private JWTAuthenticator authenticator;

    public void filter(RequestContext requestContext) {
        logger.debug("in RequestAuthFilter.filter method");
        HttpServletRequest request = requestContext.getRequest();
        logger.debug(getRequestDetails(request));

        BaseApiRequest baseApiRequest = BaseApiRequestBuilder.build(request);

        String traceId = request.getHeader(HttpConstants.HEADER_TRACEID);
        String requestIp = request.getRemoteAddr();
        String authorization = request.getHeader(HttpConstants.HEADER_AUTHORIZATION);
        if (authorization == null) {
            logger.fatal("Authorization header missing for requestIp={} " + HttpConstants.HEADER_TRACEID
                    + "={}", requestIp, traceId);
             ApiException ape = ApiExceptionFactory.get("GNAUE0001");
             throw ape;
        }
        baseApiRequest.setApiUri(request.getServletPath());
        AuthResult authResult = authenticator.authenticate(baseApiRequest);
        if (!authResult.isValid()) {
            logger.fatal("Request authorization failed for traceId={}", traceId);
            ApiException globalEx = ApiExceptionFactory.get(authResult.getErrorCode());
            throw globalEx;
        }
        requestContext.set("authRequest", baseApiRequest);
        return;
    }

    private String getRequestDetails(HttpServletRequest request) {
        return "method=" + request.getMethod() + "\n"
                + "URI=" + request.getRequestURI() + "\n"
                + "URL=" + request.getRequestURL() + "\n"
                + "protocol=" + request.getProtocol() + "\n"
                + "remote addr=" + request.getRemoteAddr() + "\n"
                + "remote host=" + request.getRemoteHost() + "\n"
                + "remote port=" + request.getRemotePort() + "\n"
                + "local addr=" + request.getLocalAddr() + "\n"
                + "local name=" + request.getLocalName() + "\n"
                + "local port=" + request.getLocalPort() + "\n"
                + "content type=" + request.getContentType() + "\n"
                + "server name=" + request.getServerName() + "\n"
                + "server port=" + request.getServerPort() + "\n"
                + "scheme=" + request.getScheme() + "\n"
                + "auth type=" + request.getAuthType();
    }


}

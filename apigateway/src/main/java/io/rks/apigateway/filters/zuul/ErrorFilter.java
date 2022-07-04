package io.rks.apigateway.filters.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.rks.apigateway.exceptions.ApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

import static com.rks.mcommon.constants.CommonConstants.INTERNAL_SERVER_ERROR;

public class ErrorFilter extends ZuulFilter {
    Logger logger = LogManager.getLogger(ErrorFilter.class);

    public String filterType() {
        return "error";
    }

    public int filterOrder() {
        return -1;
    }

    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        //return ctx.getThrowable() != null;
        return true;
    }

    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
       // HttpServletRequest request = context.getRequest();
        logger.info("in ErrorFilter.run method");
        ApiException apiException = getApiException(context.getThrowable());
        //RequestDispatcher dispatcher = request.getRequestDispatcher()
        context.remove("throwable");
        context.setResponseBody(apiException.toJsonString());
        context.getResponse().setStatus(apiException.getStatus());
        context.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
        return null;
    }

    protected ApiException getApiException(Throwable throwable) {
        logger.info("in ErrorFilter.getApiException method");
        logger.info(throwable.getCause().getMessage());
        ApiException apiException = new ApiException(500, INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);

        if (throwable.getCause() instanceof ApiException) {
            logger.debug("ApiException:");
            logger.debug("error: " + throwable.getCause());
            logger.debug("message: " + throwable.getCause().getMessage());
            return (ApiException) throwable.getCause();
        }
        if (throwable.getCause() instanceof ZuulException) {
            logger.debug("ZuulException 1:");
            logger.debug("error: " + throwable.getCause());
            logger.debug("message: " + throwable.getCause().getMessage());
            return apiException;
        }
        if (throwable instanceof ZuulException) {
            logger.debug("ZuulException 2:");
            if (throwable.getCause().getCause().getCause() != null) {
                logger.debug("error: " + throwable.getCause().getCause().getCause());
                logger.debug("message: " + throwable.getCause().getCause().getCause().getMessage());
                apiException.setMessage(throwable.getCause().getCause().getCause().getMessage());
            }
            return apiException;
            //throw new ZuulException(throwable.getCause(), 500, throwable.getMessage());
        }
        return apiException;
    }
}










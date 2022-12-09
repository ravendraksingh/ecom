package io.rks.apigateway.filters.response;

import com.google.common.io.CharStreams;
import com.netflix.zuul.context.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class ResponseLogFilter {
    Logger logger = LogManager.getLogger(ResponseLogFilter.class);

    public Object filter(RequestContext requestContext) {
        logger.info("in ResponseLogFilter.filter method");

        HttpServletRequest request = requestContext.getRequest();
        try (final InputStream responseDataStream = requestContext.getResponseDataStream()) {
            if (responseDataStream == null) {
                logger.info("Response BODY : {}", "");
                return null;
            }
            String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, StandardCharsets.UTF_8));
            logger.info("Response BODY: {}", responseData);
            requestContext.setResponseBody(responseData);
        } catch (IOException e) {
            e.printStackTrace();
//            try {
//                throw new ZuulException(e, INTERNAL_SERVER_ERROR.value(), e.getMessage());
//            } catch (ZuulException zuulException) {
//                zuulException.printStackTrace();
//            }
        }
        return null;
    }

}

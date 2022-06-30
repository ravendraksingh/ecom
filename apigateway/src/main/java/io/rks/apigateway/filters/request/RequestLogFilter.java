package io.rks.apigateway.filters.request;

import com.netflix.zuul.context.RequestContext;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestLogFilter {
    Logger logger = LogManager.getLogger(RequestLogFilter.class);

    public void filter(RequestContext requestContext) {
        logger.info("in RequestLogFilter.filter method");

        HttpServletRequest request = requestContext.getRequest();
        Map<String, List<String>> headersMap = Collections.list(request.getHeaderNames())
                .stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        h -> Collections.list(request.getHeaders(h))
                ));

        logger.info("Request HEADERS: ");
        if (!headersMap.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            headersMap.keySet().forEach(key -> {
                //logger.info(key + "=" + headersMap.get(key));
                sb.append("\n" + key + "=" + headersMap.get(key));
            });
            String requestHeaders = new String(sb);
            logger.info(requestHeaders);
        }

        logger.info(request.getRequestURL());
        try {
            logger.info("Request BODY: ", getRequestBody(request));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = request.getInputStream();
        IOUtils.copy(in, out);
        byte[] requestBytes = out.toByteArray();
        byte[] newBytes = new byte[requestBytes.length];
        System.arraycopy(requestBytes, 0, newBytes,0, requestBytes.length);
        String requestBody = new String(requestBytes, StandardCharsets.UTF_8);
        //logger.info("RequestBody: " + requestBody);
        return requestBody;
    }
}

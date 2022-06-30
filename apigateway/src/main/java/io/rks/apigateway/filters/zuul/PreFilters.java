package io.rks.apigateway.filters.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.rks.apigateway.filters.request.RequestAuthFilter;
import io.rks.apigateway.filters.request.RequestLogFilter;
import org.springframework.beans.factory.annotation.Autowired;

public class PreFilters extends ZuulFilter {

    @Autowired
    private RequestAuthFilter requestAuthentication;
    @Autowired
    private RequestLogFilter requestLogging;

    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 1;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        requestLogging.filter(requestContext);
        requestAuthentication.filter(requestContext);
        return null;
    }
}

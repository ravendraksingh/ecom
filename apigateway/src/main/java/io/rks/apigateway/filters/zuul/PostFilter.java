package io.rks.apigateway.filters.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.rks.apigateway.filters.response.ResponseLogFilter;
import org.springframework.beans.factory.annotation.Autowired;

public class PostFilter extends ZuulFilter {
    @Autowired
    private ResponseLogFilter responseLogging;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        responseLogging.filter(context);
        return null;
    }
}

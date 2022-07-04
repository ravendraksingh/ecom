package io.rks.apigateway;

import io.rks.apigateway.filters.zuul.ErrorFilter;
import io.rks.apigateway.filters.zuul.PostFilter;
import io.rks.apigateway.filters.zuul.PreFilters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class ApigatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);
    }

    @Bean
    public PreFilters preFilters() {
        return new PreFilters();
    }
    @Bean
    public PostFilter postFilters() { return new PostFilter(); }
    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

}

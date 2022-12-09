package com.rks.orderservice;

//import com.rks.orderservice.config.DatabaseCreds;
//import com.rks.orderservice.config.OrderServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

//@EnableConfigurationProperties({DatabaseCreds.class, OrderServiceConfig.class})
@SpringBootApplication
public class OrderServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
    @Autowired
    public void setEnv(Environment e) {
        System.out.println("server.port: " + e.getProperty("server.port"));
    }


   /* @RestController
    class ServiceInstanceRestController {

        @Autowired
        private DiscoveryClient discoveryClient;

        @GetMapping("/service-instances/{appName}")
        public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String appName) {
            return this.discoveryClient.getInstances(appName);
        }
    }*/
}

package com.libqa.springcloudzookeeperclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
@EnableDiscoveryClient
public class RouteDiscoveryClient {

    @Autowired
    private RouteFeignClient feign;

    public String howling() {
        return feign.howling();
    }

    public String product() {
        return feign.product();
    }

}

package com.libqa.springcloudzookeeperclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

    @Autowired
    private RouteDiscoveryClient routeDiscoveryClient;

    @GetMapping("/howling")
    public String howling() {
        return routeDiscoveryClient.howling();
    }

    @GetMapping("/product")
    public String product() {
        return routeDiscoveryClient.product();
    }

}
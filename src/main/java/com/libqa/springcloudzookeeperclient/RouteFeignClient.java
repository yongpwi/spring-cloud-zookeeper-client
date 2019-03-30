package com.libqa.springcloudzookeeperclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "howling-client")
public interface RouteFeignClient {

    @RequestMapping(path = "/howling", method = RequestMethod.GET)
    @ResponseBody
    String howling();

    @RequestMapping(path = "/product", method = RequestMethod.GET)
    @ResponseBody
    String product();
}
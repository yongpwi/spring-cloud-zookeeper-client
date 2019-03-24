package com.libqa.springcloudzookeeperclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
public class SpringCloudZookeeperClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudZookeeperClientApplication.class, args);
	}

    @RestController
    class ServiceInstanceRestController {

        @LoadBalanced
        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @Autowired
        private DiscoveryClient discoveryClient;

        @RequestMapping("/service-instances/{applicationName}")
        public List<ServiceInstance> serviceInstancesByApplicationName(
                @PathVariable String applicationName) {
            return this.discoveryClient.getInstances(applicationName);
        }

        @GetMapping("/howling")
        public String howling() {
            return "howling-8080~!!!";
        }

        @GetMapping("/callHowling/{applicationIndex}")
        public String callHowling(@PathVariable Integer applicationIndex) {
            List<ServiceInstance> clients = discoveryClient.getInstances("howling-client");
            String uri = clients.get(applicationIndex).getUri().toString();
            String requestUrl = uri + "/howling";
            this.discoveryClient.getServices().forEach(client ->
                    System.out.println(client));
            ResponseEntity<String> stockResponse =
                    restTemplate().exchange(requestUrl, HttpMethod.GET, null, new ParameterizedTypeReference<String>() {});

            return stockResponse.getBody();
        }
    }
}

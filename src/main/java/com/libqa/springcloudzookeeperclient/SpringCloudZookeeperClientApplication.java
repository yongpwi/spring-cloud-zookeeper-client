package com.libqa.springcloudzookeeperclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudZookeeperClientApplication {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudZookeeperClientApplication.class, args);
	}

    @RestController
    class ServiceInstanceRestController {

        @Autowired
        private DiscoveryClient discoveryClient;

        @RequestMapping("/service-instances/{applicationName}")
        public List<ServiceInstance> serviceInstancesByApplicationName(
                @PathVariable String applicationName) {
            return this.discoveryClient.getInstances(applicationName);
        }

//        @GetMapping("/howling")
//        public String howling() {
//            return "howling-8080~!!!";
//        }

        @GetMapping("/callHowling")
        public List<String> callHowling() {
            List<ServiceInstance> clients = discoveryClient.getInstances("libqa-client");
            discoveryClient.getInstances("howling-client").forEach(client -> clients.add(client));

            List<String> services = this.discoveryClient.getServices();

            if (clients != null && clients.size() > 0) {
                clients.forEach(serviceInstance -> services.add(serviceInstance.getUri().toString()));
            }

            return services;
        }

        @GetMapping("/callHowling2")
        public String callHowling2() {
            String result = restTemplate().getForEntity("http://howling-client/howling", String.class).getBody();

            return result;
        }
    }
}

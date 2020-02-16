package com.alibaba.nacos.consumer.controller;

import com.alibaba.nacos.consumer.feign.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("nacos")
@RefreshScope
public class ConsumeController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    Client client;

    @RequestMapping(value = "/consume/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://nacos-provider/nacos/config/" + str, String.class);
    }

    @RequestMapping(value = "/consume1/{str}", method = RequestMethod.GET)
    public String echo1(@PathVariable String str) {

        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider");

        String url = serviceInstance.getUri() + "/nacos/config/" + str;

        return  new RestTemplate().getForObject(url, String.class);

    }

    @RequestMapping(value = "/consume2/{str}", method = RequestMethod.GET)
    public Mono<String> echo2(@PathVariable String str) {
        Mono<String> stringMono = webClientBuilder.build().get()
                .uri("http://nacos-provider/nacos/config/" + str)
                .retrieve()
                .bodyToMono(String.class);

        return stringMono;
    }

    @RequestMapping(value = "/consume3/{str}", method = RequestMethod.GET)
    public String echo3(@PathVariable String str) {
        String hello = client.hello();
        return hello;
    }

}

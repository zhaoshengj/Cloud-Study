package com.alibaba.nacos.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("nacos")
@RefreshScope
public class ConsumeController {

    private final RestTemplate restTemplate;

    @Autowired
    public ConsumeController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}


    @RequestMapping(value = "/consume/{str}", method = RequestMethod.GET)
    public String echo(@PathVariable String str) {
        return restTemplate.getForObject("http://nacos-provider/nacos/config/" + str, String.class);
    }

}

package com.alibaba.nacos.provider.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("nacos")
@RefreshScope
public class NacosController {

    @Value("${nacos.msg}")
    private String msg;

    @RequestMapping(value = "/config/{str}",method = RequestMethod.GET)
    public String nacosConfig(@PathVariable String str){
        return "Hello "+str+" -- "+msg;
    }



}

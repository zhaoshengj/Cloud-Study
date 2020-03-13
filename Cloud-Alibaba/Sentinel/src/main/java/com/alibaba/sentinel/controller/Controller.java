package com.alibaba.sentinel.controller;


import com.alibaba.cloud.sentinel.datasource.config.NacosDataSourceProperties;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("nacos")
@RefreshScope
public class Controller {

    @Value("${sentinel.msg}")
    private String msg;

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/config/{str}",method = RequestMethod.GET)
    public String sentinel(@PathVariable String str){

        testService.doSomeThing(str);

        return "Hello "+str+" -- "+msg;
    }




}

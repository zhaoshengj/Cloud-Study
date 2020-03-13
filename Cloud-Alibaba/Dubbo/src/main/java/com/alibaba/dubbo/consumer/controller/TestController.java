package com.alibaba.dubbo.consumer.controller;

import com.alibaba.dubbo.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Reference
    HelloService helloService;

    @GetMapping("/test")
    public String test() {
        return helloService.hello("dubbo test");
    }
}

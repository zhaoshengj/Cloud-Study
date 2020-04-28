package com.zsj.consul.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsulController {

    @RequestMapping("/provider")
    public String consul(){
        return "Consul Provider";
    }
}

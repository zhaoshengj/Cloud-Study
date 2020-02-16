package com.alibaba.nacos.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("nacos-provider")
public interface Client {

    @GetMapping("/nacos/config/feign")
    String hello();
}

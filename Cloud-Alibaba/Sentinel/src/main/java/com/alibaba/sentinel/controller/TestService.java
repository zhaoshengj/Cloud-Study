package com.alibaba.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @SentinelResource(value = "doSomeThing",blockHandler = "exceptionHandler")
    public void doSomeThing(String str) {
        System.out.println(str);
    }

    // 限流与阻塞处理
    public void exceptionHandler(String str, BlockException ex) {
        System.out.println( "blockHandler：" + str + ex);
    }
}

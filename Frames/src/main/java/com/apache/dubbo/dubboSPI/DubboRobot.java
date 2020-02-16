package com.apache.dubbo.dubboSPI;

import com.alibaba.dubbo.common.extension.SPI;

@SPI//dubboSPI需要注释
public interface DubboRobot {


    void sayHello();
}
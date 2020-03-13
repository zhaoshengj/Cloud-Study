package com.apache.dubbo.dubboSPI;

public class Bumblebee implements DubboRobot {
    @Override
    public void sayHello() {
        System.out.println("Hello, I am dubbo Bumblebee.");
    }
}

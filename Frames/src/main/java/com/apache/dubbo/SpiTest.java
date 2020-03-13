package com.apache.dubbo;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.apache.dubbo.ZSJSPI.ZSJRobot;
import com.apache.dubbo.ZSJSPI.loader.ZSJExtensionLoader;
import com.apache.dubbo.dubboSPI.DubboRobot;
import com.apache.dubbo.jdkSPI.Robot;
import org.junit.Test;

import java.util.ServiceLoader;

public class SpiTest {

    @Test
    public void dubboSpiSayHello() throws Exception {
        ExtensionLoader<DubboRobot> extensionLoader =
                ExtensionLoader.getExtensionLoader(DubboRobot.class);
        DubboRobot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        DubboRobot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }

    @Test
    public void javaSpiSayHello() throws Exception {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }

    @Test
    public void zsjSpiSayHello() throws Exception {
        ZSJExtensionLoader<ZSJRobot> extensionLoader = ZSJExtensionLoader.getExtensionLoader(ZSJRobot.class);
        ZSJRobot defaultExtension = extensionLoader.getDefaultExtension();
        defaultExtension.sayHello();

        ZSJRobot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();

        ZSJRobot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();

    }
}

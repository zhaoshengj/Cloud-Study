package com.apache.dubbo.ZSJSPI.annotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ZSJSPI {

    String value() default "";

}

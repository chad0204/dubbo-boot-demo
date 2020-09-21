package com.pc.dubboprovider;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 *
 */
@DubboComponentScan(basePackages = "com.pc.dubboprovider.service.impl")
@SpringBootApplication(scanBasePackages = {"com.pc.tpt","com.pc.dubboprovider"})
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }

}

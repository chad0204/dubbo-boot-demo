package com.pc.dubboconsumer.controller;


import com.pc.dubboapi.serviceapi.CallService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;


@RestController
public class CallController {

    @Reference(loadbalance="random")
    private CallService callService;


    //http://localhost:8082/call
    @RequestMapping("/call")
    public String call() {
        return callService.call();
    }

    @PostConstruct
    public void init() {
        System.out.println(callService.call());
    }

}

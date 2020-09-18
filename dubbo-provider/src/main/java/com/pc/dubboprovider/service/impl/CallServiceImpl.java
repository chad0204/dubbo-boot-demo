package com.pc.dubboprovider.service.impl;


import com.pc.dubboapi.serviceapi.CallService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;


@Component
@Service//这个注解不是Spring的，是dubbo用于暴露服务的
public class CallServiceImpl implements CallService{

    @Override
    public String call() {
        return "start success!";
    }
}

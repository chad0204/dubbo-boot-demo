package com.pc.dubboprovider.service.impl;


import com.pc.dubboapi.serviceapi.CallService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;


@Component
@Service//这个注解不是Spring的，是dubbo用于暴露服务的
public class CallServiceImpl implements CallService{

    @Override
    public String call() {

        //测试DEGRADE_GRADE_EXCEPTION_COUNT，可以看到这里只抛出5个异常，其余的请求没有进来
//        throw new RuntimeException("bizException");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "call success!";
    }

    @Override
    @Deprecated
    public String fallback() {
        return "fallback";
    }
}

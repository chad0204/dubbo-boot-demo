package com.pc.dubboapi.serviceapi;



public interface CallService {

    String call();

    //自己在try catch中调用，不准确，应该通过DubboFallbackRegistry.setProviderFallback()注册降级方法
    String fallback();

}

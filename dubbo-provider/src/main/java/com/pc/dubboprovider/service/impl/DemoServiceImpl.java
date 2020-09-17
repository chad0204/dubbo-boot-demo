package com.pc.dubboprovider.service.impl;


import com.pc.dubboapi.service.DemoService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Service//这个注解不是Spring的，是dubbo用于暴露服务的
public class DemoServiceImpl implements DemoService {

    private Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);



    @Override
    public String call(String name) {

        logger.info("name:[{}]",name);

        return name;
    }
}

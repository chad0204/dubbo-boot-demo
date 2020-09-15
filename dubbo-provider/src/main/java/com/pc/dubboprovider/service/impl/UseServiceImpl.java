package com.pc.dubboprovider.service.impl;


import com.pc.dubboapi.model.UserAddress;
import com.pc.dubboapi.model.UserParam;
import com.pc.dubboapi.service.UserService;
import com.pc.dubboprovider.service.InnerUserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service//这个注解不是Spring的，是dubbo用于暴露服务的
public class UseServiceImpl implements UserService,InnerUserService {

    public UserAddress getUserAddress(UserParam userParam) {

        List<UserAddress> list = new ArrayList<>();
        list.add(new UserAddress(1L,"鸣人","木叶村"));
        list.add(new UserAddress(2L,"路飞","东海"));

        for (UserAddress userAddr:list) {
            if(userParam.getId().equals(userAddr.getId())) {
                return userAddr;
            } else {
                System.err.println("id 错误");
            }
        }


        return null;
    }


    @Override
    public UserAddress getUserAddress(Long uid) {
        return null;
    }
}

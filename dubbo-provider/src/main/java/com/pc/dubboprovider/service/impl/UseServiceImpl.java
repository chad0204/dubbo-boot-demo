package com.pc.dubboprovider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.pc.dubboapi.model.UserAddress;
import com.pc.dubboapi.service.UserService;
import com.pc.dubboprovider.service.InnerUserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Service//这个注解不是Spring的，是dubbo用于暴露服务的
public class UseServiceImpl implements UserService,InnerUserService {


    public UserAddress getUserAddress(Long uid) {

        List<UserAddress> list = new ArrayList<>();
        list.add(new UserAddress(1L,"鸣人","木叶村"));
        list.add(new UserAddress(2L,"路飞","东海"));

        for (UserAddress userAddr:list
             ) {
            if(uid.equals(userAddr.getId())) {
                return userAddr;
            } else {

            }
        }
        return null;
    }
}

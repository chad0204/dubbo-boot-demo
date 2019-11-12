package com.pc.dubboconsumer.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pc.dubboapi.model.UserAddress;
import com.pc.dubboapi.service.OrderService;
import com.pc.dubboapi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {


    //@Autowired
    @Reference(loadbalance="random")
    UserService userService;

    public UserAddress initOrder(Long uid) {

        UserAddress userAddress = userService.getUserAddress(uid);

        System.out.println(userAddress.getUsername()+":"+userAddress.getAddress());

        return userAddress;

    }
}

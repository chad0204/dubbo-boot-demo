package com.pc.dubboconsumer.service.impl;

import com.pc.dubboapi.model.UserAddress;
import com.pc.dubboapi.model.UserParam;
import com.pc.dubboapi.service.OrderService;
import com.pc.dubboapi.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {


//    @Autowired
    @Reference(loadbalance="random")
    UserService userService;


    public UserAddress initOrder(Long uid) {


        UserParam param = new UserParam();
        param.setId(uid);
        UserAddress userAddress = userService.getUserAddress(param);

        Optional.ofNullable(userAddress).ifPresent(x -> {
            System.out.println(x.getUsername()+":"+x.getAddress());
        });
        return userAddress;

    }
}

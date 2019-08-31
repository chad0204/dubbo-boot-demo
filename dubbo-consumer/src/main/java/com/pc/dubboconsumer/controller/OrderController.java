package com.pc.dubboconsumer.controller;


import com.pc.dubboapi.model.UserAddress;
import com.pc.dubboapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    //http://localhost:8082/initOrder?id=1
    @RequestMapping("/initOrder")
    public UserAddress initOrder(Long id) {
        return orderService.initOrder(id);
    }
}

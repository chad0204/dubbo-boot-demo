package com.pc.dubboconsumer.controller;


import com.pc.dubboapi.command.model.request.ARequest;
import com.pc.dubboapi.command.service.IService;
import com.pc.dubboapi.model.UserAddress;
import com.pc.dubboapi.service.OrderService;
import com.pc.dubboapi.service.UserService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    private ApplicationContext context;


    //http://localhost:8082/initOrder?id=1
    @RequestMapping("/initOrder")
    public UserAddress initOrder(Long id) {


        context.getBean("orderServiceImpl");

        return orderService.initOrder(id);
    }
}

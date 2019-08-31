package com.pc.dubboapi.service;

import com.pc.dubboapi.model.UserAddress;

public interface OrderService {

    UserAddress initOrder(Long uid);
}

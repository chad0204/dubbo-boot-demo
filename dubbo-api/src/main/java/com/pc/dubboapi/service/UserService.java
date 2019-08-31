package com.pc.dubboapi.service;


import com.pc.dubboapi.model.UserAddress;

public interface UserService {

    UserAddress getUserAddress(Long uid);
}

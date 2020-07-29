package com.pc.dubboprovider.service;

import com.pc.dubboapi.model.UserAddress;

/**
 * TODO
 *
 * @author dongxie
 * @date 15:59 2019-11-21
 */
public interface InnerUserService {

    UserAddress getUserAddress(Long uid);
}

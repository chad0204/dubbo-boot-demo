package com.pc.dubboapi.model;

import java.io.Serializable;

/**
 * TODO
 *
 * @author pengchao
 * @date 10:20 2020-09-09
 */
public class UserParam implements Serializable {

    private Long id;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

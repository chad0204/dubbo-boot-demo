package com.pc.dubboapi.model;

import java.io.Serializable;

public class UserAddress implements Serializable {

    private static final long serialVersionUID = 5098049650305960117L;
    private Long id;
    private String username;
    private String address;


    public UserAddress(Long id, String username, String address) {
        this.id = id;
        this.username = username;
        this.address = address;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


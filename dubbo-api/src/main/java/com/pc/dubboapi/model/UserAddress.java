package com.pc.dubboapi.model;

import java.io.Serializable;

public class UserAddress implements Serializable {

    private static final long serialVersionUID = 5098049650305960117L;
    private Long id;
    private String username;
    private String number;


    public UserAddress(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserAddress(Long id, String username, String number) {
        this.id = id;
        this.username = username;
        this.number = number;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}


package com.uma.gymfit.user.security.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String user;

    private String password;


    public AuthenticationReq(String user, String password) {
        this.user = user;
        this.password = password;
    }
}

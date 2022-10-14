package com.uma.gymfit.user.security.model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class TokenInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String jwtToken;

    public TokenInfo(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}

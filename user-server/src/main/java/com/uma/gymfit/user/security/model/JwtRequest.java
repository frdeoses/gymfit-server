package com.uma.gymfit.user.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtRequest {

    private String username;

    private String password;


}

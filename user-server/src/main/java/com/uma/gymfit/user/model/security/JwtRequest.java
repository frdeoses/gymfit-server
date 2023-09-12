package com.uma.gymfit.user.model.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtRequest {

    private String username;

    private String password;


}

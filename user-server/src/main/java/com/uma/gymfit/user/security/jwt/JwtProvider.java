package com.uma.gymfit.user.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtProvider {

//    Clave para verificar token
    @Value("${jwt.secret}")
    private String secret;

//    Tiempo de expiracion de la clave
    @Value("${jwt.expiration}")
    private int expiration;

}

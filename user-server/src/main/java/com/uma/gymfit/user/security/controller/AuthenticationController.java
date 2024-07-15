package com.uma.gymfit.user.security.controller;

import com.uma.gymfit.user.converters.ConvertUserToUserRS;
import com.uma.gymfit.user.exception.UserAutenticationException;
import com.uma.gymfit.user.model.dto.UserRS;
import com.uma.gymfit.user.model.security.JwtRequest;
import com.uma.gymfit.user.model.security.JwtResponse;
import com.uma.gymfit.user.security.config.JwtUtils;
import com.uma.gymfit.user.security.service.impl.UserDetailsServiceImpl;
import com.uma.gymfit.user.utils.Literals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@Slf4j
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtUtils jwtUtils;

    private final ConvertUserToUserRS convertUserToUserRS;

    @Autowired
    public AuthenticationController(final ConvertUserToUserRS convertUserToUserRS,
                                    final JwtUtils jwtUtils,
                                    final UserDetailsServiceImpl userDetailsService,
                                    final AuthenticationManager authenticationManager) {
        this.convertUserToUserRS = convertUserToUserRS;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(Literals.GENERATE_TOKEN)
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) {
        try {
            autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (Exception exception) {
            log.error(exception.getMessage());
            exception.printStackTrace();
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void autenticar(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException exception) {
            log.error("USUARIO DESHABILITADO " + exception.getMessage());
            throw new UserAutenticationException("USUARIO DESHABILITADO " + exception.getMessage());
        } catch (BadCredentialsException e) {
            log.error("Credenciales invalidas " + e.getMessage());
            throw new UserAutenticationException("Credenciales invalidas " + e.getMessage());
        }
    }

    @GetMapping(Literals.CURRENT_USER)
    public UserRS getCurrentUser(Principal principal) {
        return convertUserToUserRS.convert(this.userDetailsService.loadUserByUsername(principal.getName()));
    }
}

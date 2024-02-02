package com.uma.gymfit.calendar.security.controller;

import com.uma.gymfit.calendar.config.JwtUtils;
import com.uma.gymfit.calendar.exception.UserAutenticationException;
import com.uma.gymfit.calendar.model.security.JwtRequest;
import com.uma.gymfit.calendar.model.security.JwtResponse;
import com.uma.gymfit.calendar.model.user.User;
import com.uma.gymfit.calendar.security.service.impl.UserDetailsServiceImpl;
import com.uma.gymfit.calendar.utils.Literals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

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
    public User getCurrentUser(Principal principal) {
        return (User) this.userDetailsService.loadUserByUsername(principal.getName());
    }
}

package com.uma.gymfit.user.controller;

import com.uma.gymfit.user.model.ResponseHTTP;
import com.uma.gymfit.user.model.User;
import com.uma.gymfit.user.security.model.AuthenticationReq;
import com.uma.gymfit.user.security.model.TokenInfo;
import com.uma.gymfit.user.security.service.JwtUtilService;
import com.uma.gymfit.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping("/api/gymfit")
@Slf4j
public class UserController {

    @Autowired
    private IUserService gymFitService;

    @Autowired
    UserDetailsService usuarioDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/user/authenticate")
    public ResponseEntity<TokenInfo> authenticate(@RequestBody AuthenticationReq authenticationReq) {
        log.info("Autenticando al usuario {}", authenticationReq.getUser());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationReq.getUser(),
                        authenticationReq.getPassword()));

        final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(
                authenticationReq.getUser());

        final String jwt = jwtUtilService.generateToken(userDetails);

        TokenInfo tokenInfo = new TokenInfo(jwt);

        return ResponseEntity.ok(tokenInfo);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        List<User> allUser = new ArrayList<>();
        allUser = gymFitService.allUser();
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @GetMapping("/users/{idUser}")
    public ResponseEntity<User> findUser(@PathVariable String idUser) {
        User user;
        try {
            user = gymFitService.findUser(idUser);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), user, null);
            return new ResponseEntity(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), idUser, e.getMessage());
            return new ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseHTTP> createUser(@Validated @RequestBody User user) {

        try {
            gymFitService.createUser(user);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.CREATED.value(), HttpStatus.CREATED.toString(), user, null);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), user, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PutMapping("/user")
    public ResponseEntity<ResponseHTTP> updateUser(@RequestBody User user) {

        try {
            gymFitService.updateUser(user);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), user, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), user, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseHTTP> deleteUser(@PathVariable String id) {

        try {
            gymFitService.deleteUser(id);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), id, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), id, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}

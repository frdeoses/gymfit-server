package com.uma.gymfit.user.controller;

import com.uma.gymfit.user.exception.UserException;
import com.uma.gymfit.user.model.ResponseHTTP;
import com.uma.gymfit.user.model.User;
import com.uma.gymfit.user.service.IUserService;
import com.uma.gymfit.user.utils.Literals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping(Literals.API)
public class UserController {

    @Autowired
    private IUserService gymFitService;

    @GetMapping(Literals.USERS)
    public ResponseEntity<List<User>> allUsers() {
        List<User> allUser = gymFitService.allUser();
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @GetMapping(Literals.USER_ID)
    public ResponseEntity<User> findUser(@PathVariable String idUser) {
        User user;
        try {
            user = gymFitService.findUser(idUser);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), user, null, Literals.USER_ID);
            return new ResponseEntity(res, HttpStatus.OK);
        } catch (UserException e) {
            ResponseHTTP res = new ResponseHTTP(e.getCode(), idUser, e.getMessage(), Literals.USER_ID);
            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(Literals.USER)
    public ResponseEntity<ResponseHTTP> createUser(@Validated @RequestBody User user) {

        try {
            gymFitService.createUser(user);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.CREATED.value(), user, null, Literals.USER);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (UserException e) {
            ResponseHTTP res = new ResponseHTTP(e.getCode(), user, e.getMessage(), Literals.USER_ID);
            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping(Literals.USER)
    public ResponseEntity<ResponseHTTP> updateUser(@RequestBody User user) {

        try {
            gymFitService.updateUser(user);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), user, null, Literals.USER);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserException e) {
            ResponseHTTP res = new ResponseHTTP(e.getCode(), user, e.getMessage(), Literals.USER_ID);
            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
        }

    }


    @DeleteMapping(Literals.USER_ID)
    public ResponseEntity<ResponseHTTP> deleteUser(@PathVariable String idUser) {

        try {
            gymFitService.deleteUser(idUser);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), idUser, null, Literals.USER_ID);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (UserException e) {
            ResponseHTTP res = new ResponseHTTP(e.getCode(), idUser, e.getMessage(), Literals.USER_ID);
            return new ResponseEntity(res, HttpStatus.BAD_REQUEST);
        }

    }


}

package com.uma.gymfit.user.controller;

import com.uma.gymfit.user.model.ResponseHTTP;
import com.uma.gymfit.user.model.User;
import com.uma.gymfit.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping("/api/gymfit")
public class UserController {

    @Autowired
    private IUserService gymFitService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> allUser = new ArrayList<>();
        allUser = gymFitService.allUser();
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @GetMapping("/users/{idUser}")
    public ResponseEntity<User> findUser(@PathVariable String idUser) {
        User user;
        try {
            user = gymFitService.findUser(idUser);
            return new ResponseEntity(user, HttpStatus.OK);
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

package com.uma.gymfit.user.controller;

import com.uma.gymfit.user.model.user.ResponseHTTP;
import com.uma.gymfit.user.model.user.User;
import com.uma.gymfit.user.service.IUserService;
import com.uma.gymfit.user.utils.Literals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PATCH})
@RequestMapping(Literals.API)
public class UserController {

    @Autowired
    private IUserService gymFitService;

    @GetMapping(Literals.USERS)
    public ResponseEntity<ResponseHTTP<List<User>>> allUsers() {
        return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, gymFitService.allUser(), null));
    }

    @GetMapping(Literals.USERS_ROLE_USER)
    public ResponseEntity<ResponseHTTP<List<User>>> allUsersRoleUsers() {
        return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, gymFitService.allUserRoleUsers(), null));

    }

    @GetMapping(Literals.USER_ID)
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

    @PostMapping(Literals.USER)
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

    @PatchMapping(Literals.USER)
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

    @DeleteMapping(Literals.USER_ID)
    public ResponseEntity<ResponseHTTP> deleteUser(@PathVariable String idUser) {

        try {
            gymFitService.deleteUser(idUser);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), idUser, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), idUser, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

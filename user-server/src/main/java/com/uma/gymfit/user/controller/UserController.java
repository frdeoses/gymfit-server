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

import java.net.URI;
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
    public ResponseEntity<ResponseHTTP<?>> findUser(@PathVariable String idUser) {
        try {
            return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, gymFitService.findUser(idUser), null));
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, idUser, e.getMessage()));
        }
    }

    @PostMapping(Literals.USER)
    public ResponseEntity<ResponseHTTP<User>> createUser(@Validated @RequestBody User user) {

        try {
            gymFitService.createUser(user);
            return ResponseEntity.created(URI.create(Literals.USER)).body(createResponseHttp(HttpStatus.CREATED, user, null));
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }

    }

    @PatchMapping(Literals.USER)
    public ResponseEntity<ResponseHTTP<User>> updateUser(@RequestBody User user) {

        try {
            gymFitService.updateUser(user);
            return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, user, null));
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }

    }

    @DeleteMapping(Literals.USER_ID)
    public ResponseEntity<ResponseHTTP<String>> deleteUser(@PathVariable String idUser) {

        try {
            gymFitService.deleteUser(idUser);
            return ResponseEntity.ok(createResponseHttp(HttpStatus.OK, idUser, null));
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }


    }

    private <T> ResponseHTTP<T> createResponseHttp(HttpStatus httpStatus, T object, String message) {
        return new ResponseHTTP<>(httpStatus.value(), httpStatus.toString(), object, message);
    }

}

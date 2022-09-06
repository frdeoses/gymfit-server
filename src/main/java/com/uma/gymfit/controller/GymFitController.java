package com.uma.gymfit.controller;

import com.uma.gymfit.model.ResponseHTTP;
import com.uma.gymfit.model.TablaEntrenamiento;
import com.uma.gymfit.model.Usuario;
import com.uma.gymfit.service.IGymFitService;
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
public class GymFitController {

    @Autowired
    private IGymFitService gymFitService;

    @GetMapping("/users")
    public ResponseEntity<List<Usuario>> allUsers() {
        List<Usuario> allUser = new ArrayList<>();
        allUser = gymFitService.allUser();
        return new ResponseEntity<>(allUser, HttpStatus.OK);
    }

    @GetMapping("/training-tables")
    public ResponseEntity<List<TablaEntrenamiento>> allTrainingTable() {
        return new ResponseEntity<>(gymFitService.allTrainingTable(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseHTTP> createUser(@Validated @RequestBody Usuario usuario) {

        try {
            gymFitService.createUser(usuario);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.CREATED.value(), HttpStatus.CREATED.toString(), usuario, null);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), usuario, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/training-table")
    public ResponseEntity<ResponseHTTP> createTrainingTable(@Validated @RequestBody TablaEntrenamiento tablaEntrenamiento) {

        try {
            gymFitService.createTrainingTable(tablaEntrenamiento);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.CREATED.value(), HttpStatus.CREATED.toString(), tablaEntrenamiento, null);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), tablaEntrenamiento, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/user")
    public ResponseEntity<ResponseHTTP> updateUser(@RequestBody Usuario usuario) {

        try {
            gymFitService.updateUser(usuario);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), usuario, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), usuario, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/training-table")
    public ResponseEntity<ResponseHTTP> updateTrainingTable(@RequestBody TablaEntrenamiento tablaEntrenamiento) {

        try {
            gymFitService.updateTrainingTable(tablaEntrenamiento);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), tablaEntrenamiento, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), tablaEntrenamiento, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/training-table/{id}")
    public ResponseEntity<ResponseHTTP> deleteTrainingTable(@PathVariable String id) {

        try {
            gymFitService.deleteTrainingTable(id);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(),  id, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), id, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ResponseHTTP> deleteUser(@PathVariable String id) {

        try {
            gymFitService.deleteUser(id);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(),  id, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), id, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}

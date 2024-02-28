package com.uma.gymfit.trainingtable.controller;

import com.uma.gymfit.trainingtable.model.ResponseHTTP;
import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.service.IGymMachineService;
import com.uma.gymfit.trainingtable.utils.Literals;
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
public class GymMachineController {

    @Autowired
    private IGymMachineService gymMachineService;

    @GetMapping(Literals.GYM_MACHINES)
    public ResponseEntity<ResponseHTTP<List<GymMachine>>> allGymMachine() {

        return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, gymMachineService.allGymMachine(), null));
    }

    @GetMapping(Literals.GYM_MACHINE_ID)
    public ResponseEntity<ResponseHTTP<GymMachine>> findGymMachine(@PathVariable String idGymMachine) {

        try {
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, gymMachineService.findGymMachine(idGymMachine), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));

        }
    }

    @PostMapping(Literals.GYM_MACHINE)
    public ResponseEntity<ResponseHTTP<GymMachine>> createGymMachine(@Validated @RequestBody GymMachine gymMachine) {

        try {
            gymMachineService.createGymMachine(gymMachine);
            return ResponseEntity.created(URI.create(Literals.GYM_MACHINE)).body(Literals.createResponseHttp(HttpStatus.OK, gymMachine, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));

        }
    }

    @PatchMapping(Literals.GYM_MACHINE)
    public ResponseEntity<ResponseHTTP<GymMachine>> updateGymMachine(@RequestBody GymMachine gymMachine) {

        try {
            gymMachineService.updateGymMachine(gymMachine);
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, gymMachine, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));

        }

    }

    @DeleteMapping(Literals.GYM_MACHINE_ID)
    public ResponseEntity<ResponseHTTP<String>> deleteGymMachine(@PathVariable String idGymMachine) {

        try {
            gymMachineService.deleteGymMachine(idGymMachine);
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, idGymMachine, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, idGymMachine, e.getMessage()));

        }

    }

    @DeleteMapping(Literals.GYM_MACHINE_EMPTY_REPOSITORY)
    public ResponseEntity<ResponseHTTP<String>> deleteAllGymMachine() {

        try {
            gymMachineService.deleteAllGymMachine();
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, null, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));

        }

    }


}

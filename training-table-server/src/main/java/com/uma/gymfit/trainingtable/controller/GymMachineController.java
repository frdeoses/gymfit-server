package com.uma.gymfit.trainingtable.controller;

import com.uma.gymfit.trainingtable.model.ResponseHTTP;
import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.service.IGymMachineService;
import com.uma.gymfit.trainingtable.utils.Literals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping(Literals.API)
public class GymMachineController {
    private IGymMachineService gymMachineService;

    @GetMapping(Literals.GYM_MACHINES)
    public ResponseEntity<List<GymMachine>> allGymMachine() {
        return new ResponseEntity<>(gymMachineService.allGymMachine(), HttpStatus.OK);
    }

    @GetMapping(Literals.GYM_MACHINE_ID)
    public ResponseEntity<GymMachine> findGymMachine(@PathVariable String idGymMachine) {

        GymMachine gymMachine;
        try {
            gymMachine = gymMachineService.findGymMachine(idGymMachine);
            return new ResponseEntity(gymMachine, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), idGymMachine, e.getMessage());
            return new ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(Literals.GYM_MACHINE)
    public ResponseEntity<ResponseHTTP> createGymMachine(@Validated @RequestBody GymMachine gymMachine) {

        try {
            gymMachineService.createGymMachine(gymMachine);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.CREATED.value(), HttpStatus.CREATED.toString(), gymMachine, null);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), gymMachine, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(Literals.GYM_MACHINE)
    public ResponseEntity<ResponseHTTP> updateGymMachine(@RequestBody GymMachine gymMachine) {

        try {
            gymMachineService.updateGymMachine(gymMachine);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), gymMachine, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), gymMachine, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(Literals.GYM_MACHINE_ID)
    public ResponseEntity<ResponseHTTP> deleteGymMachine(@PathVariable String idGymMachine) {

        try {
            gymMachineService.deleteGymMachine(idGymMachine);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), idGymMachine, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), idGymMachine, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(Literals.GYM_MACHINE_EMPTY_REPOSITORY)
    public ResponseEntity<ResponseHTTP> deleteAllGymMachine() {

        try {
            gymMachineService.deleteAllGymMachine();
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), null, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), null, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}

package com.uma.gymfit.trainingtable.controller;

import com.uma.gymfit.trainingtable.model.ResponseHTTP;
import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.model.training.TrainingType;
import com.uma.gymfit.trainingtable.model.user.User;
import com.uma.gymfit.trainingtable.service.IGymMachineService;
import com.uma.gymfit.trainingtable.service.ITrainingService;
import com.uma.gymfit.trainingtable.service.ITrainingTableService;
import com.uma.gymfit.trainingtable.utils.Literals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping(Literals.API)
public class TrainingTableController {
    
    @Autowired
    private ITrainingTableService trainingTableService;

    @Autowired
    private IGymMachineService gymMachineService;

    @Autowired
    private ITrainingService trainingService;


    @GetMapping(Literals.TRAINING_TABLES)
    public ResponseEntity<List<TrainingTable>> allTrainingTable() {
        return new ResponseEntity<>(trainingTableService.allTrainingTable(), HttpStatus.OK);
    }

    @GetMapping(Literals.TYPE_TRAINING)
    public ResponseEntity<TrainingType[]> allTrainingType() {
        return new ResponseEntity<>(trainingService.trainingType(), HttpStatus.OK);
    }

    @GetMapping(Literals.GYM_MACHINES)
    public ResponseEntity<List<GymMachine>> allGymMachine() {
        return new ResponseEntity<>(gymMachineService.allGymMachine(), HttpStatus.OK);
    }

    @GetMapping(Literals.TRAININGS)
    public ResponseEntity<List<Training>> allTraining() {
        return new ResponseEntity<>(trainingService.allTraining(), HttpStatus.OK);
    }


    @GetMapping(Literals.TRAINING_TABLE_ID)
    public ResponseEntity<TrainingTable> findTrainingTable(@PathVariable String idTrainingTable) {

        TrainingTable trainingTable;
        try {
            trainingTable = trainingTableService.findTrainingTable(idTrainingTable);
            return new ResponseEntity(trainingTable, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), idTrainingTable, e.getMessage());
            return new ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(Literals.TRAINING_TABLE_TYPE)
    public ResponseEntity<List<TrainingTable>> findByTrainingType(@RequestParam String typeTraining, @RequestParam String idUser) {

        List<TrainingTable> trainingTables;
        try {
            trainingTables = trainingTableService.findByTrainingType(typeTraining, idUser);
            return new ResponseEntity(trainingTables, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), typeTraining, e.getMessage());
            return new ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(Literals.TRAINING_TABLE_USER)
    public ResponseEntity<List<TrainingTable>> findByUser(@RequestBody User user) {

        List<TrainingTable> trainingTables;
        try {
            trainingTables = trainingTableService.findByUser(user);
            return new ResponseEntity(trainingTables, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), user, e.getMessage());
            return new ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(Literals.TRAININGS_BY_USER)
    public ResponseEntity<List<Training>> listTrainingByUser(@RequestBody User user) {

        List<Training> trainingList;
        try {
            trainingList = trainingService.findTrainingsByUser(user);
            return new ResponseEntity(trainingList, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), user, e.getMessage());
            return new ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(Literals.TRAININGS_BY_TYPE_TRAINING)
    public ResponseEntity<List<Training>> findTrainingByTrainingType(@RequestParam String typeTraining, @RequestParam String idUser) {

        List<Training> trainingList;
        try {
            trainingList = trainingService.findTrainingsByTrainingType(typeTraining, idUser);
            return new ResponseEntity(trainingList, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), typeTraining, e.getMessage());
            return new ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

    @GetMapping(Literals.TRAINING_ID)
    public ResponseEntity<Training> findTraining(@PathVariable String idTraining) {

        Training training;
        try {
            training = trainingService.findTraining(idTraining);
            return new ResponseEntity(training, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), idTraining, e.getMessage());
            return new ResponseEntity(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(Literals.TRAINING_TABLE)
    public ResponseEntity<ResponseHTTP> createTrainingTable(@Validated @RequestBody TrainingTable trainingTable) {

        try {
            trainingTableService.createTrainingTable(trainingTable);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.CREATED.value(), HttpStatus.CREATED.toString(), trainingTable, null);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), trainingTable, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PostMapping(Literals.TRAINING)
    public ResponseEntity<ResponseHTTP> createTraining(@Validated @RequestBody Training training) {

        try {
            trainingService.createTraining(training);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.CREATED.value(), HttpStatus.CREATED.toString(), training, null);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), training, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(Literals.TRAINING_TABLE)
    public ResponseEntity<ResponseHTTP> updateTrainingTable(@RequestBody TrainingTable trainingTable) {

        try {
            trainingTableService.updateTrainingTable(trainingTable);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), trainingTable, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), trainingTable, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(Literals.TRAINING)
    public ResponseEntity<ResponseHTTP> updateTraining(@RequestBody Training training) {

        try {
            trainingService.updateTraining(training);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), training, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), training, e.getMessage());
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

    @DeleteMapping(Literals.TRAINING_TABLE_ID)
    public ResponseEntity<ResponseHTTP> deleteTrainingTable(@PathVariable String idTrainingTable) {

        try {
            trainingTableService.deleteTrainingTable(idTrainingTable);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), idTrainingTable, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), idTrainingTable, e.getMessage());
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

    @DeleteMapping(Literals.TRAINING_ID)
    public ResponseEntity<ResponseHTTP> deleteTraining(@PathVariable String idTraining) {

        try {
            trainingService.deleteTraining(idTraining);
            ResponseHTTP res = new ResponseHTTP(HttpStatus.OK.value(), HttpStatus.OK.toString(), idTraining, null);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            ResponseHTTP res = new ResponseHTTP(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), idTraining, e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}

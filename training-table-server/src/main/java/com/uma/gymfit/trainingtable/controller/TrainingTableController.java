package com.uma.gymfit.trainingtable.controller;

import com.uma.gymfit.trainingtable.model.ResponseHTTP;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.model.user.User;
import com.uma.gymfit.trainingtable.service.ITrainingTableService;
import com.uma.gymfit.trainingtable.utils.Literals;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT})
@RequestMapping(Literals.API)
public class TrainingTableController {

    private ITrainingTableService trainingTableService;

    @GetMapping(Literals.TRAINING_TABLES)
    public ResponseEntity<List<TrainingTable>> allTrainingTable() {
        return new ResponseEntity<>(trainingTableService.allTrainingTable(), HttpStatus.OK);
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

}

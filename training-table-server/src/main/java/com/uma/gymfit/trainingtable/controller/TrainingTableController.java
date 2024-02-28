package com.uma.gymfit.trainingtable.controller;

import com.uma.gymfit.trainingtable.model.ResponseHTTP;
import com.uma.gymfit.trainingtable.model.training.TrainingTable;
import com.uma.gymfit.trainingtable.service.ITrainingTableService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PATCH})
@RequestMapping(Literals.API)
public class TrainingTableController {

    @Autowired
    private ITrainingTableService trainingTableService;

    @GetMapping(Literals.TRAINING_TABLES)
    public ResponseEntity<ResponseHTTP<List<TrainingTable>>> allTrainingTable() {

        return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, trainingTableService.allTrainingTable(), null));
    }

    @GetMapping(Literals.TRAINING_TABLE_ID)
    public ResponseEntity<ResponseHTTP<TrainingTable>> findTrainingTable(@PathVariable String idTrainingTable) {

        TrainingTable trainingTable;
        try {
            trainingTable = trainingTableService.findTrainingTable(idTrainingTable);

            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, trainingTable, null));
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @GetMapping(Literals.TRAINING_TABLE_TYPE)
    public ResponseEntity<ResponseHTTP<List<TrainingTable>>> findByTrainingType(@RequestParam String typeTraining, @RequestParam String idUser) {

        List<TrainingTable> trainingTables;
        try {
            trainingTables = trainingTableService.findByTrainingType(typeTraining, idUser);

            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, trainingTables, null));
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @GetMapping(Literals.TRAINING_TABLE_USER)
    public ResponseEntity<ResponseHTTP<List<TrainingTable>>> findByUser(@PathVariable String userId) {

        List<TrainingTable> trainingTables;
        try {
            trainingTables = trainingTableService.findByUser(userId);

            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, trainingTables, null));
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PostMapping(Literals.TRAINING_TABLE)
    public ResponseEntity<ResponseHTTP<TrainingTable>> createTrainingTable(@Validated @RequestBody TrainingTable trainingTable) {

        try {
            trainingTableService.createTrainingTable(trainingTable);

            return ResponseEntity.created(URI.create(Literals.TRAINING_TABLE)).body(Literals.createResponseHttp(HttpStatus.OK, trainingTable, null));
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @PatchMapping(Literals.TRAINING_TABLE)
    public ResponseEntity<ResponseHTTP<TrainingTable>> updateTrainingTable(@RequestBody TrainingTable trainingTable) {

        try {
            trainingTableService.updateTrainingTable(trainingTable);
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, trainingTable, null));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }

    }

    @DeleteMapping(Literals.TRAINING_TABLE_ID)
    public ResponseEntity<ResponseHTTP<String>> deleteTrainingTable(@PathVariable String idTrainingTable) {

        try {
            trainingTableService.deleteTrainingTable(idTrainingTable);
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, idTrainingTable, null));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }

    }

}

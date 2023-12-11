package com.uma.gymfit.trainingtable.controller;

import com.uma.gymfit.trainingtable.model.ResponseHTTP;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.training.TrainingType;
import com.uma.gymfit.trainingtable.model.user.User;
import com.uma.gymfit.trainingtable.service.ITrainingService;
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
public class TrainingController {

    @Autowired
    private ITrainingService trainingService;

    @GetMapping(Literals.TYPE_TRAINING)
    public ResponseEntity<TrainingType[]> allTrainingType() {
        return new ResponseEntity<>(trainingService.trainingType(), HttpStatus.OK);
    }

    @GetMapping(Literals.TRAININGS)
    public ResponseEntity<List<Training>> allTraining() {
        return new ResponseEntity<>(trainingService.allTraining(), HttpStatus.OK);
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

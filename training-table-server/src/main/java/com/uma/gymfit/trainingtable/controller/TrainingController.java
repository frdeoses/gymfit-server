package com.uma.gymfit.trainingtable.controller;

import com.uma.gymfit.trainingtable.model.ResponseHTTP;
import com.uma.gymfit.trainingtable.model.dtos.NewWorkedWeight;
import com.uma.gymfit.trainingtable.model.dtos.TrainingDto;
import com.uma.gymfit.trainingtable.model.training.Training;
import com.uma.gymfit.trainingtable.model.training.TrainingType;
import com.uma.gymfit.trainingtable.service.ITrainingService;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PATCH})
@RequestMapping(Literals.API)
public class TrainingController {

    private final ITrainingService trainingService;

    @Autowired
    public TrainingController(ITrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping(Literals.TYPE_TRAINING)
    public ResponseEntity<ResponseHTTP<TrainingType[]>> allTrainingType() {
        return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, trainingService.trainingType(), null));
    }

    @GetMapping(Literals.TRAININGS)
    public ResponseEntity<ResponseHTTP<List<Training>>> allTraining() {
        return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, trainingService.allTraining(), null));
    }

    @GetMapping(Literals.TRAININGS_BY_USER)
    public ResponseEntity<ResponseHTTP<List<Training>>> listTrainingByUser(@PathVariable String userId) {

        try {
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, trainingService.findTrainingsByUser(userId), null));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @GetMapping(Literals.TRAININGS_BY_TYPE_TRAINING)
    public ResponseEntity<ResponseHTTP<List<Training>>> findTrainingByTrainingType(@RequestParam String typeTraining, @RequestParam String idUser) {

        List<Training> trainingList;
        try {
            trainingList = trainingService.findTrainingsByTrainingType(typeTraining, idUser);
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, trainingList, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));
        }
    }

    @GetMapping(Literals.TRAINING_ID)
    public ResponseEntity<ResponseHTTP<Training>> findTraining(@PathVariable String idTraining) {

        Training training;
        try {
            training = trainingService.findTraining(idTraining);
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, training, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));

        }
    }

    @PostMapping(Literals.TRAINING)
    public ResponseEntity<ResponseHTTP<Training>> createTraining(@Validated @RequestBody TrainingDto trainingDto) {

        try {
            return ResponseEntity.created(URI.create(Literals.TRAINING)).body(Literals.createResponseHttp(HttpStatus.OK, trainingService.createTraining(trainingDto), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));

        }
    }

    @PatchMapping(Literals.TRAINING)
    public ResponseEntity<ResponseHTTP<Training>> updateTraining(@RequestBody @Valid TrainingDto trainingDto) {

        try {
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, trainingService.updateTraining(trainingDto), null));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));

        }
    }

    @DeleteMapping(Literals.TRAINING_ID)
    public ResponseEntity<ResponseHTTP<String>> deleteTraining(@PathVariable String idTraining) {

        try {
            trainingService.deleteTraining(idTraining);
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, idTraining, null));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, idTraining, e.getMessage()));

        }
    }

    @PostMapping(Literals.WORKED_WEIGHTS)
    public ResponseEntity<ResponseHTTP<NewWorkedWeight>> addNewWorkedWeights(@RequestBody @Valid NewWorkedWeight newWorkedWeight) {

        try {
            trainingService.addNewWorkedWeights(newWorkedWeight);
            return ResponseEntity.ok(Literals.createResponseHttp(HttpStatus.OK, newWorkedWeight, null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Literals.createResponseHttp(HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage()));

        }
    }


}

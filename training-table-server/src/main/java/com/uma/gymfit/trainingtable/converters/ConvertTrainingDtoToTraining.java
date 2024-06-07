package com.uma.gymfit.trainingtable.converters;

import com.uma.gymfit.trainingtable.model.dtos.TrainingDto;
import com.uma.gymfit.trainingtable.model.training.Training;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

@Component
public class ConvertTrainingDtoToTraining {

    public Training convert(TrainingDto trainingDto) {
        return Training.builder()
                .name(trainingDto.getName())
                .typeTraining(trainingDto.getTypeTraining())
                .like(trainingDto.getLike())
                .userId(trainingDto.getUserId())
                .lastUpdateDate(LocalDateTime.now())
                .creationDate(LocalDateTime.now())
                .listWorkedWeights(Objects.isNull(trainingDto.getListWorkedWeights()) ? new ArrayList<>() : trainingDto.getListWorkedWeights())
                .caloriesBurned(trainingDto.getCaloriesBurned())
                .description(trainingDto.getDescription())
                .exercisedArea(trainingDto.getExercisedArea())
                .gymMachine(Objects.isNull(trainingDto.getGymMachine()) ? null : trainingDto.getGymMachine())
                .needBeSupervised(trainingDto.isNeedBeSupervised())
                .numSeries(trainingDto.getNumSeries())
                .numRepetitions(trainingDto.getNumRepetitions())
                .build();
    }

}

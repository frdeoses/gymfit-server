package com.uma.gymfit.trainingtable.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uma.gymfit.trainingtable.model.training.GymMachine;
import com.uma.gymfit.trainingtable.model.training.WorkedWeight;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class TrainingDto implements Serializable {

    String id;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    String name;

    String typeTraining;

    int numRepetitions;

    int numSeries;

    String exercisedArea;

    String description;

    int like;

    boolean needBeSupervised;

    int caloriesBurned;

    transient List<WorkedWeight> listWorkedWeights;

    GymMachine gymMachine;

    String userId;


}

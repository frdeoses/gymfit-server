package com.uma.gymfit.trainingtable.model.training;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document(value = "Training")
@Getter
@Setter
@ToString
public class Training implements Serializable {

    @Id
    private String id;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String name;

    private String typeTraining;

    private int numRepetitions;

    private int numSeries;

    private String exercisedArea;

    private String description;

    private int like;

    private boolean needBeSupervised;

    private int caloriesBurned;

    private List<WorkedWeights> listWorkedWeights;

    private GymMachine gymMachine;

    private String userId;

    private LocalDateTime creationDate;

    private LocalDateTime lastUpdateDate;

}

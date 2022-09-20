package com.uma.gymfit.trainingtable.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class Training {

    @Id
    @NotNull
    @NotBlank
    private String id;

    private String typeTraining;

    private int numRepetitions;

    private int numSeries;

    private String exercisedArea;

    private String explication;

    private int like;

    private List<WorkedWeights> listWorkedWeights;
    
    private GymMachine gymMachine;

}

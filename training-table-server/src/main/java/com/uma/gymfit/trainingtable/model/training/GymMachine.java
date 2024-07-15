package com.uma.gymfit.trainingtable.model.training;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document(value = "GymMachine")
@Data
public class GymMachine implements Serializable {

    @Id
    private String id;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String name;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String model;

    private int numMachine;

    private String exercisedArea;

    private String description;

    private List<WorkedWeight> listWorkedWeights;

    private int like;

    private LocalDateTime creationDate;

    private LocalDateTime lastUpdateDate;


}

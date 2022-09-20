package com.uma.gymfit.trainingtable.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Getter
@Setter
public class GymMachine {

    @Id
    @NotNull
    @NotBlank
    private String id;

    private String name;

    private String exercisedArea;

    private String description;

    private List<WorkedWeights> listWorkedWeights;

    private int like;

}

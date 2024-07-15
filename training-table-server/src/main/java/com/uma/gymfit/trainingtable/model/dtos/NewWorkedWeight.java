package com.uma.gymfit.trainingtable.model.dtos;

import com.uma.gymfit.trainingtable.model.training.WorkedWeight;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@Value
@Valid
public class NewWorkedWeight implements Serializable {

    @NotNull
    String trainingId;

    WorkedWeight workedWeight;
}

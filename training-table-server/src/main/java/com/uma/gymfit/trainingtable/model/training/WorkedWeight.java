package com.uma.gymfit.trainingtable.model.training;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class WorkedWeight implements Serializable {

    private double weight;

    private LocalDateTime date;

    @NotNull
    private String userId;

    private int serie;

}

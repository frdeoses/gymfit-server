package com.uma.gymfit.trainingtable.model.training;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class WorkedWeights implements Serializable {
    
    private double weight;

    private LocalDateTime date;

    private int serie;

}

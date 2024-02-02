package com.uma.gymfit.trainingtable.model.user;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Weight implements Serializable {

    private LocalDateTime date;

    private double weightData;

}

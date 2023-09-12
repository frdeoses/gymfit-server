package com.uma.gymfit.user.model.user;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FatPercentage implements Serializable {

    private LocalDateTime fecha;

    private double porcentajeGrasa;

}

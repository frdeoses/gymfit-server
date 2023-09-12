package com.uma.gymfit.calendar.model.user;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class FatPercentage implements Serializable {

    private long fecha;

    private double porcentajeGrasa;

}

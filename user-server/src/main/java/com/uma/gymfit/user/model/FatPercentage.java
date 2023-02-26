package com.uma.gymfit.user.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class FatPercentage {

    private LocalDateTime fecha;

    private double porcentajeGrasa;

}

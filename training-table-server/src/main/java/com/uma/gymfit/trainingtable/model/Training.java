package com.uma.gymfit.trainingtable.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class Ejercicio {

    @Id
    @NotNull
    @NotBlank
    private String id;

    private String tipoEjercicio;

    private int numRepeticiones;

    private int numSeries;

    private String zonaEjercitada;

    private String explicacion;

    private int meGusta;

    private List<PesoTrabajado> pesosTrabajados;
    
    private Maquina maquina;

}

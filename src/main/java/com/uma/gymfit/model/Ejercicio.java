package com.uma.gymfit.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
public class Ejercicio {

    @Id
    private String id;

    private String tipoEjercicio;

    private int numRepeticiones;

    private int numSeries;

    private String zonaEjercitada;

    private String explicacion;

    private int like;

    private List<PesoTrabajado> pesosTrabajados;
    
    private Maquina maquina;

}

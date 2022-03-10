package com.uma.gymfit.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Getter
@Setter
public class Maquina {

    @Id
    private String id;

    private String nombre;

    private String zonaEjercitada;

    private String descripcion;

    private List<PesoTrabajado> pesosTrabajados;

    private int meGusta;

}

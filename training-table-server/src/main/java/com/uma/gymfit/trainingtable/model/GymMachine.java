package com.uma.gymfit.trainingtable.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Getter
@Setter
public class Maquina {

    @Id
    @NotNull
    @NotBlank
    private String id;

    private String nombre;

    private String zonaEjercitada;

    private String descripcion;

    private List<PesoTrabajado> pesosTrabajados;

    private int meGusta;

}

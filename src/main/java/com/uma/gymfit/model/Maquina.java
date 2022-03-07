package com.uma.gymfit.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Maquina")
@Data
@Getter
@Setter
public class Maquina {

    private String id;

    private String nombre;

    private String zonaEjercitada;

    private String descripcion;

    private int like;

}

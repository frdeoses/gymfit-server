package com.uma.gymfit.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "TablaEntrenamiento")
@Data
@Getter
@Setter
@ToString
public class TablaEntrenamiento {

    @Id
    private String id;

    private String idUsuario;

    private long fechaCreacion;

    private String tipoEntreno;

    private long fechaInicio;

    private long fechaFin;

    private int duracionEntrenamiento;

    private int tiempoDescanso;

    private String obserevacion;

    private List<Ejercicio> ejercicios;


}

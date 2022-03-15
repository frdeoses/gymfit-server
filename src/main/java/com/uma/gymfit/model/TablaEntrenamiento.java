package com.uma.gymfit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(required = true)
    private long fechaCreacion;

    @JsonProperty(required = true)
    private String tipoEntreno;

    @JsonProperty(required = true)
    private long fechaInicio;

    private long fechaFin;

    private int duracionEntrenamiento;

    private int tiempoDescanso;

    private String obserevacion;

    private List<Ejercicio> ejercicios;


}

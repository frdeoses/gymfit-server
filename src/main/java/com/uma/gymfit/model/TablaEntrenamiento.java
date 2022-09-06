package com.uma.gymfit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document(value = "TablaEntrenamiento")
@Data
@Getter
@Setter
@ToString
public class TablaEntrenamiento {

    @Id
    private String id;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String idUsuario;

    @JsonProperty(required = true)
    @NotNull
    private long fechaCreacion;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String tipoEntreno;

    @NotNull
    @JsonProperty(required = true)
    private long fechaInicio;

    private long fechaFin;

    private int duracionEntrenamiento;

    private int tiempoDescanso;

    private String obserevacion;

    private List<Ejercicio> ejercicios;


}

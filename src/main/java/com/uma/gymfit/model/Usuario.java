package com.uma.gymfit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document(value = "Usuario")
@Data
@Getter
@Setter
@ToString
public class Usuario {

    @Id
    private String id;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String nombre;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String apellidos;

    @Indexed(unique = true)
    @NotNull
    @NotBlank
    private String email;

    @JsonProperty(required = true)
    @NotNull
    private long fechaNacimiento;

    @JsonProperty(required = true)
    @NotNull
    private long fechaInscripcion;

    @JsonProperty(required = true)
    private double altura;

    private List<Peso> pesoUsuario;

    private List<PGrasa> porcentajeGrasa;

}

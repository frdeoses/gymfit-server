package com.uma.gymfit.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "Usuario")
@Data
@Getter
@Setter
@ToString
public class Usuario {

    @Id
    private String id;

    private String nombre;

    private String apellidos;

    @Indexed(unique = true)
    private String email;

    private long fechaNacimiento;

    private long fechaInscripcion;

    private double altura;

    private List<Peso> pesoUsuario;

    private List<PGrasa> porcentajeGrasa;

}

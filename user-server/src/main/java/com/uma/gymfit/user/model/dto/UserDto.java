package com.uma.gymfit.user.model.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class UserDto implements Serializable {

    @Id
    String id;

    @NotBlank
    @NotNull(message = "El campo username no puede ser nulo...")
    String username;

    @NotBlank
    @NotNull(message = "El campo password no puede ser nulo...")
    String password;

    @NotNull(message = "El campo surname no puede ser nulo...")
    @NotBlank
    String surname;

    @NotNull(message = "El campo name no puede ser nulo...")
    @NotBlank
    String name;

    @NotNull(message = "El email no puede ser nulo...")
    @NotBlank
    String email;

    @NotNull(message = "El campo username no puede ser nulo...")
    String phone;

    @NotNull(message = "El campo username no puede ser nulo...")
    double weight;

    @NotNull(message = "El campo username no puede ser nulo...")
    double height;

    @NotNull(message = "El campo birthDate no puede ser nulo...")
    LocalDateTime birthDate;

}

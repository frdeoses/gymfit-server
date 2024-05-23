package com.uma.gymfit.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uma.gymfit.user.model.user.FatPercentage;
import com.uma.gymfit.user.model.user.UserRol;
import com.uma.gymfit.user.model.user.Weight;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Value
@Builder(toBuilder = true)
public class UserRS implements Serializable {

    @Id
    String id;

    @NotBlank
    @JsonProperty(required = true)
    @NotNull(message = "El campo username no puede ser nulo...")
    String username;

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

    double weight;

    double height;

    @NotNull(message = "El campo birthDate no puede ser nulo...")
    LocalDateTime birthDate;

    int caloriesBurned;

    int heartRate;

    @NotNull(message = "El campo userRoles no puede ser nulo...")
    transient Set<UserRol> userRoles;

    transient List<Weight> listUserWeight;

    transient List<FatPercentage> listFatPercentage;

}

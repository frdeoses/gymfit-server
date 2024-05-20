package com.uma.gymfit.calendar.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PersonalData implements Serializable {
    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String name;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String surname;

    @Indexed(unique = true)
    @NotNull
    @NotBlank
    @Email
    private String email;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String phone;

    @JsonProperty(required = true)
    @NotNull
    private LocalDateTime birthDate;
}

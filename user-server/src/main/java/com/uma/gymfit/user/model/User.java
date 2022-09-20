package com.uma.gymfit.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document(value = "User")
@Data
@Getter
@Setter
@ToString
public class User {

    @Id
    private String id;

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
    private long birthdate;

    @JsonProperty(required = true)
    @NotNull
    private long registrationDate;

    @JsonProperty(required = true)
    private double height;

    private List<Weight> listUserWeight;

    private List<FatPercentage> listFatPercentage;

}

package com.uma.gymfit.trainingtable.model.training;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uma.gymfit.trainingtable.model.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(value = "TrainingTable")
@Data
@Getter
@Setter
@ToString
public class TrainingTable implements Serializable {

    @Id
    private String id;

    @JsonProperty(required = true)
    @NotNull
    private User user;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String name;

    private String description;

    @JsonProperty(required = true)
    @NotNull
    private LocalDateTime creationDate;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String typeTraining;

    @NotNull
    @JsonProperty(required = true)
    private LocalDate initDate;

    private LocalDate endDate;

    private int trainingDuration;

    private int breakTime;

    private int caloriesBurned;

    private String observation;

    private List<Training> listTraining;

    private LocalDateTime lastUpdateDate;


}

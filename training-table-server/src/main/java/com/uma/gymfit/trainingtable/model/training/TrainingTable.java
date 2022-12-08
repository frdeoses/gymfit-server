package com.uma.gymfit.trainingtable.model.training;

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

@Document(value = "TrainingTable")
@Data
@Getter
@Setter
@ToString
public class TrainingTable {

    @Id
    private String id;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String idUser;

    @JsonProperty(required = true)
    @NotNull
    private long creationDate;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String typeTraining;

    @NotNull
    @JsonProperty(required = true)
    private long initDate;

    private long endDate;

    private int trainingDuration;

    private int breakTime;

    private String observation;

    private List<Training> listTraining;


}

package com.uma.gymfit.calendar.model.calendar;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Calendar implements Serializable {

    private static final long serialVersionUID = 2790992461526821035L;

    @Id
    private String id;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    private String title;

    private String description;

    private boolean published;

    private LocalDateTime creationDate;

    private LocalDateTime lastUpdateDate;

    private List<Comment> comments;

}

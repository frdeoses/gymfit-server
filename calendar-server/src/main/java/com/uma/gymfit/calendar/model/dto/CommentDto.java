package com.uma.gymfit.calendar.model.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Value
@Builder(toBuilder = true)
public class CommentDto implements Serializable {

    @Id
    String id;

    @NotBlank
    String comment;

    @NotNull(message = "No puede ser nulo el id del usuario")
    @NotBlank
    String username;

    @NotBlank
    @NotNull(message = "No puede ser nulo el id del evento")
    String idCalendar;

}

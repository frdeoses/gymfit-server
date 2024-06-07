package com.uma.gymfit.calendar.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uma.gymfit.calendar.model.calendar.Comment;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class CalendarDto implements Serializable {

    private static final long serialVersionUID = 2790992461526821035L;

    @Id
    String id;

    @JsonProperty(required = true)
    @NotNull
    @NotBlank
    String title;

    String description;

    boolean published;

    transient List<Comment> comments;

}

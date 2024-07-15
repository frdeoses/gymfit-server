package com.uma.gymfit.calendar.model.calendar;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class Comment implements Serializable {

    private static final long serialVersionUID = 883275625458432253L;

    String id;

    String userName;

    String text;

    LocalDateTime date;
}

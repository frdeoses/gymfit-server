package com.uma.gymfit.calendar.model.calendar;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
@Builder(toBuilder = true)
public class Comment implements Serializable {

    String id;

    String userName;

    String text;

    LocalDateTime date;
}

package com.uma.gymfit.calendar.model.calendar;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Comment implements Serializable {
    
    private String id;

    private String idUser;

    private String userName;

    private String text;

    private LocalDateTime date;
}

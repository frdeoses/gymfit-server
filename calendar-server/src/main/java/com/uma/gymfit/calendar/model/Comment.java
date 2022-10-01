package com.uma.gymfit.calendar.model;

import lombok.Data;

@Data
public class Comment {

    private String id;

    private String idUser;

    private String userName;

    private long date;
}

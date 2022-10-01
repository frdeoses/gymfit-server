package com.uma.gymfit.calendar.model;

import lombok.Data;

import java.util.List;

@Data
public class Calendar {

    private String id;

    private String title;

    private String description;

    private List<Comment> comments;

}

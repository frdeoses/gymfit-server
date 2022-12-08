package com.uma.gymfit.calendar.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResponseHTTP {

    private int code;

    private Object body;

    private String error;

    private String path;


}

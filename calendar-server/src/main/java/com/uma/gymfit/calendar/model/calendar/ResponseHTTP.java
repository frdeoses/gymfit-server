package com.uma.gymfit.calendar.model.calendar;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHTTP {

    private int code;

    private String response;

    private Object body;

    private String error;


}

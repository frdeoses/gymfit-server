package com.uma.gymfit.calendar.model.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHTTP<T> {

    private int code;

    private String response;

    private T body;

    private String error;


}

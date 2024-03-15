package com.uma.gymfit.trainingtable.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResponseHTTP<T> {

    private int code;

    private String response;

    private T body;

    private String error;


}

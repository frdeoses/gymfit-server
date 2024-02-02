package com.uma.gymfit.trainingtable.model;

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

    private String response;

    private Object body;

    private String error;


}

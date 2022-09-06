package com.uma.gymfit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

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

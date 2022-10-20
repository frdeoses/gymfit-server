package com.uma.gymfit.user.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseHTTP {

    private int code;

    private String response;

    private Object body;

    private String error;


}

package com.uma.gymfit.user.exception;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Data
public class UserException extends Exception{

    private int code;

    private String message;

    private Object value;

    private String path;


    public UserException(HttpStatus typeError, Object value, String path){

        this.code = typeError.value();

        this.value = value;

        this.path = path;

        this.message = createMessageError(typeError);



    }

    private String createMessageError(HttpStatus typeError) {

        String message = "";

        switch (typeError){

            case NOT_FOUND:
                message = "ERROR: User no se encuentra en el sistema...";
                log.error("ERROR: User no se encuentra en el sistema...");
                break;

            default:
                message = "ERROR: Se ha producido un error no controlado en el sistema..";
                log.error("ERROR: Se ha producido un error no controlado en el sistema..");
        }

        return  message;

    }


}

package com.thoughtworks.rslist.componet;

import com.thoughtworks.rslist.Exception.Error;
import com.thoughtworks.rslist.Exception.InvalidIndexException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RsEventExceptionHandler {

    @ExceptionHandler({InvalidIndexException.class, MethodArgumentNotValidException.class})
    public ResponseEntity handleException(Exception e) {
        String errorMessage;
        if(e instanceof MethodArgumentNotValidException) {
            errorMessage = "invalid param";
        } else {
            errorMessage = e.getMessage();
        }

        Error error = new Error();
        error.setError(errorMessage);
        return ResponseEntity.badRequest().body(error);
    }

}

package com.thoughtworks.rslist.componet;

import com.thoughtworks.rslist.Exception.Error;
import com.thoughtworks.rslist.Exception.InvalidIndexException;
import com.thoughtworks.rslist.Exception.InvalidRequestParamException;
import com.thoughtworks.rslist.api.RsController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {RsController.class})
public class RsEventExceptionHandler {

    @ExceptionHandler({InvalidIndexException.class, MethodArgumentNotValidException.class, InvalidRequestParamException.class})
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

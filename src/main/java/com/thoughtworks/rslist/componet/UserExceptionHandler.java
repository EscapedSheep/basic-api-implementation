package com.thoughtworks.rslist.componet;

import com.thoughtworks.rslist.Exception.Error;
import com.thoughtworks.rslist.Exception.UserNotExistedException;
import com.thoughtworks.rslist.api.UserController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(assignableTypes = {UserController.class})
public class UserExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, UserNotExistedException.class})
    public ResponseEntity<Error> handlerInvalidUser() {
        Error error = new Error();
        error.setError("invalid user");
        log.error("invalid user");
        return ResponseEntity.badRequest().body(error);
    }
}

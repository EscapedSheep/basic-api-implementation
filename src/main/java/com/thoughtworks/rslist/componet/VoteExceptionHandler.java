package com.thoughtworks.rslist.componet;

import com.thoughtworks.rslist.Exception.Error;
import com.thoughtworks.rslist.Exception.VoteNumberOverThanOwnException;
import com.thoughtworks.rslist.api.VoteController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(assignableTypes = {VoteController.class})
public class VoteExceptionHandler {

    @ExceptionHandler({VoteNumberOverThanOwnException.class})
    public ResponseEntity<Error> handleVoteException(VoteNumberOverThanOwnException e) {
        log.error(e.getMessage());
        Error error = new Error();
        error.setError(e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}

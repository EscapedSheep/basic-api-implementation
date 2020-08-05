package com.thoughtworks.rslist.Exception;

public class InvalidRequestParamException extends RuntimeException{

    @Override
    public String getMessage() {
        return "invalid request param";
    }
}

package com.thoughtworks.rslist.Exception;

public class InvalidIndexException extends RuntimeException{

    @Override
    public String getMessage() {
        return "invalid index";
    }
}

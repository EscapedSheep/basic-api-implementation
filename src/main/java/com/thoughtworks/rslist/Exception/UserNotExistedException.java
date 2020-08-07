package com.thoughtworks.rslist.Exception;

public class UserNotExistedException extends RuntimeException{
    @Override
    public String getMessage() {
        return "user not existed!";
    }
}

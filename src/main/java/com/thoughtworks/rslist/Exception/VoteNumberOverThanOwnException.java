package com.thoughtworks.rslist.Exception;

public class VoteNumberOverThanOwnException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Vote number not enough";
    }
}

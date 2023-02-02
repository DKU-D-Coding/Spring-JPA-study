package com.dku.springstudy.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("user not found");
    }

    public UserNotFoundException(String message){
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}

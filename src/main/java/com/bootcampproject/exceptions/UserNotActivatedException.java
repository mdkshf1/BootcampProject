package com.bootcampproject.exceptions;

public class UserNotActivatedException extends RuntimeException{
    public UserNotActivatedException(String message)
    {
        super(message);
    }
}

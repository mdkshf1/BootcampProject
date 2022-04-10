package com.bootcampproject.exceptions;

public class UserDeactivateException extends RuntimeException{
    public UserDeactivateException(String messasge)
    {
        super(messasge);
    }
}

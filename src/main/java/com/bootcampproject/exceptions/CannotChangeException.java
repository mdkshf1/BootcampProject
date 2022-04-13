package com.bootcampproject.exceptions;

public class CannotChangeException extends RuntimeException{
    public CannotChangeException(String message)
    {
        super(message);
    }
}

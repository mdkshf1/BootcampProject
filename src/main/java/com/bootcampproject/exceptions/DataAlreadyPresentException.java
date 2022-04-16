package com.bootcampproject.exceptions;

public class DataAlreadyPresentException extends RuntimeException{
    public DataAlreadyPresentException(String message)
    {
        super(message);
    }
}

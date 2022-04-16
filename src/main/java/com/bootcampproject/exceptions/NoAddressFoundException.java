package com.bootcampproject.exceptions;

public class NoAddressFoundException extends RuntimeException{
    public NoAddressFoundException(String message)
    {
        super(message);
    }
}

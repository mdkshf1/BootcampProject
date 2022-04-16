package com.bootcampproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String messsge)
    {
        super(messsge);
    }
}

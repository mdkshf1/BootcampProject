package com.bootcampproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class UserAlreadyExistException extends RuntimeException{

        public UserAlreadyExistException(String message) {
            super(message);
        }
    }
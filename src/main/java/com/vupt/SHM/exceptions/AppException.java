package com.vupt.SHM.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class AppException extends RuntimeException{
    public AppException(String message) {
        super(message);
    }
}

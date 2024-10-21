package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.dtos.ErrorApp;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class HandlerExceptionController {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ErrorApp optionNotFoundException(ObjectNotFoundException ex){
        return new ErrorApp(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date());
    }
}

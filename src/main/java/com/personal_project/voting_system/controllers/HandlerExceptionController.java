package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.Enums.IconsAlerts;
import com.personal_project.voting_system.dtos.ResponseApp;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import com.personal_project.voting_system.exceptions.OccupiedAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class HandlerExceptionController {

    @Autowired
    IconsAlerts iconsAlerts;

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseApp optionNotFoundException(ObjectNotFoundException ex){
        return new ResponseApp(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date());
    }

    @ExceptionHandler(OccupiedAttributes.class)
        public ResponseApp occupiedAtributes(OccupiedAttributes ex){
            return new ResponseApp(ex.getTitle().concat(ex.getMessage()),iconsAlerts.getERROR(), HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date());
        }
}

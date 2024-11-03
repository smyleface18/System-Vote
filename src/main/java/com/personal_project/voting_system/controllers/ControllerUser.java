package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.dtos.ErrorApp;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.services.EmailService;
import com.personal_project.voting_system.services.ServiceUser;
import com.personal_project.voting_system.services.Services;
import jakarta.persistence.PersistenceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class ControllerUser {

    private final ServiceUser serviceUser;
    private final EmailService emailService;

    @Autowired
    public ControllerUser(ServiceUser serviceUser, EmailService emailService) {
        this.serviceUser = serviceUser;
        this.emailService = emailService;
    }


    @GetMapping("/user/{name}")
    public User getUser(@PathVariable String name){
        return serviceUser.getUser(name);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user ) throws Exception {
        emailService.sendEmail(user.getEmail(), "ps");
        return serviceUser.addUser(user);

    }

    @PostMapping("/validation/email/{token}")
    public void validationEmail(@PathVariable String token){

    }




}

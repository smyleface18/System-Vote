package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.services.ServiceEmail;
import com.personal_project.voting_system.services.ServiceUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class ControllerUser {

    private final ServiceUser serviceUser;
    private final ServiceEmail serviceEmail;


    @Autowired
    public ControllerUser(ServiceUser serviceUser, ServiceEmail serviceEmail) {
        this.serviceUser = serviceUser;
        this.serviceEmail = serviceEmail;
    }


    @GetMapping("/{name}")
    public User getUser(@PathVariable String name){
        return serviceUser.getUser(name);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user ) throws Exception {
        serviceEmail.sendEmailCheck(user.getEmail());
        return serviceUser.addUser(user);

    }
    @GetMapping("/validation/email/{token}")
    public ResponseEntity<?> validationEmail(@PathVariable String token){
        return serviceUser.checkEmail(token);
    }

    @PostMapping("/recheck")
    public void recheckEmail(@RequestBody Map<String,String> body){
        serviceEmail.sendEmailCheck(body.get("email"));
    }






}

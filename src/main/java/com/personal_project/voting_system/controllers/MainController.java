package com.personal_project.voting_system.controllers;


import com.personal_project.voting_system.services.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api")
public class MainController {

    private final ServiceUser serviceUser;

    @Autowired
    public MainController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @GetMapping("/")
    public String getStatus(){
        return "ValidEmail";
    }






}

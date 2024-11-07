package com.personal_project.voting_system.controllers;


import com.personal_project.voting_system.services.ServiceEmail;
import com.personal_project.voting_system.services.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping("/api")
public class MainController {

    private final ServiceUser serviceUser;
    private final ServiceEmail serviceEmail;

    @Autowired
    public MainController(ServiceUser serviceUser, ServiceEmail serviceEmail) {
        this.serviceUser = serviceUser;
        this.serviceEmail = serviceEmail;
    }

    @GetMapping("/")
    public String getStatus(){
        return "aa";
    }

    @GetMapping("/validation/email/{token}")
    public String  validationEmail(@PathVariable String token, Map<String,String> model){
        model.putAll(serviceUser.checkEmail(token));
             return "ResponseEmail";
    }

}

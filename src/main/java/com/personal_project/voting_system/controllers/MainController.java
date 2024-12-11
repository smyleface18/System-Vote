package com.personal_project.voting_system.controllers;


import com.personal_project.voting_system.services.ServiceEmail;
import com.personal_project.voting_system.services.ServiceUser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
//@RequestMapping("/api")
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
        return "ok";
    }

    @GetMapping("/validation/email/{token}")
    public String  validationEmail(@PathVariable String token, Map<String,String> model){
        model.putAll(serviceUser.checkEmail(token));
             return "ResponseEmail";
    }

    @RequestMapping(value = "/login", method = RequestMethod.OPTIONS)
    public void handleOptions(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}

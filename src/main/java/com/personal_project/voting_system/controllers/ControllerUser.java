package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.dtos.UpdataUser;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.security.TokenData;
import com.personal_project.voting_system.services.ServiceEmail;
import com.personal_project.voting_system.services.ServiceUser;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class ControllerUser {

    private static final Logger log = LoggerFactory.getLogger(ControllerUser.class);
    private final ServiceUser serviceUser;
    private final ServiceEmail serviceEmail;
    private final TokenData tokenData;

    @Autowired
    public ControllerUser(ServiceEmail serviceEmail, ServiceUser serviceUser, TokenData tokenData) {
        this.serviceEmail = serviceEmail;
        this.serviceUser = serviceUser;
        this.tokenData = tokenData;
    }

    @GetMapping("/{token}")
    public User getUser(@PathVariable String token){
        Long id = Long.valueOf(String.valueOf(tokenData.Readclaims(token).get("code")));
        return serviceUser.getUserById(id);
    }


    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user ) throws Exception {
        serviceEmail.sendEmailCheck(user.getEmail());
        return serviceUser.addUser(user);

    }

    @PostMapping("/updata")
    public ResponseEntity<?> updataUser(@RequestBody UpdataUser updataUser){
        return serviceUser.updataUser(updataUser);
    }


    @PostMapping("/recheck")
    public void recheckEmail(@RequestBody Map<String,String> body){
        serviceEmail.sendEmailCheck(body.get("email"));
    }



    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public void handleOptions(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS,PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setStatus(HttpServletResponse.SC_OK); }


}

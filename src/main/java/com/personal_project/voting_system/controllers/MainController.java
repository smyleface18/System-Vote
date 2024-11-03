package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.dtos.Information;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.services.Services;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {

    private Services services;

    @Autowired
    public MainController(Services services) {
        this.services = services;
    }

    @GetMapping("/")
    public String getStatus(){
        return "200";
    }










}

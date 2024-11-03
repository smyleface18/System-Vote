package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.security.TokenData;
import com.personal_project.voting_system.services.ServiceOption;
import com.personal_project.voting_system.services.ServiceVote;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/option")
public class ControllerOption {

    private final ServiceOption serviceOption;
    private final ServiceVote serviceVote;
    private final TokenData tokenData;

    @Autowired
    public ControllerOption(ServiceOption serviceOption, ServiceVote serviceVote, TokenData tokenData) {
        this.serviceOption = serviceOption;
        this.serviceVote = serviceVote;
        this.tokenData = tokenData;
    }

    @SneakyThrows
    @PostMapping("/voted/{id_vote}/option/{id_option}/{token}")
    public ResponseEntity<?> voted(@PathVariable("id_option") Long id_option, @PathVariable("id_vote") Long id_vote
            , @PathVariable String token){
            Long id = Long.valueOf(String.valueOf(tokenData.Readclaims(token).get("code")));
           List<User> users =  serviceVote.getVote(id_vote).getVoters().stream()
                    .filter( user -> user.getId().equals(id))
                    .toList();
        if(!users.isEmpty()){
            return new ResponseEntity<>("You have already voted!", HttpStatus.FORBIDDEN);
        }
        else {
            serviceVote.registerVoters(id,id_vote);
            serviceOption.addScore(id_option);
            return new ResponseEntity<>("Vote recorded successfully!", HttpStatus.FORBIDDEN);
        }

    }
}

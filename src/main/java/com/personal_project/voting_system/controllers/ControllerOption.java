package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.dtos.Option;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.security.TokenData;
import com.personal_project.voting_system.services.ServiceOption;
import com.personal_project.voting_system.services.ServiceVote;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body){
        serviceOption.AddOption(new Option(
                (String) body.get("title"),
                serviceVote.getVote(Long.valueOf(
                        String.valueOf(
                        body.get("idVote"))))));

        return ResponseEntity.status(HttpStatus.CREATED.value()).body("Se ha creado correctamente la opción.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Map<String, Object> body){
        serviceOption.deletOption(Long.valueOf(String.valueOf(body.get("id"))));

        return ResponseEntity.status(HttpStatus.CREATED.value()).body("Se ha eliminado correctamente la opción.");
    }
}

package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.dtos.Information;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.security.TokenData;
import com.personal_project.voting_system.services.ServiceUser;
import com.personal_project.voting_system.services.ServiceVote;
import com.personal_project.voting_system.services.VerificationViewVote;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/vote")

public class ControllerVote {

    private final ServiceVote serviceVote;
    private final ServiceUser serviceUser;
    private final TokenData tokenData;
    private final VerificationViewVote verificationViewVote;

    @Autowired
    public ControllerVote(ServiceVote serviceVote, ServiceUser serviceUser, TokenData tokenData, VerificationViewVote verificationViewVote) {
        this.serviceVote = serviceVote;
        this.serviceUser = serviceUser;
        this.tokenData = tokenData;
        this.verificationViewVote = verificationViewVote;
    }

    @GetMapping("/{idUser}/{token}/{currentDate}")
    public Optional<Vote> getVote(@PathVariable String token, @PathVariable Long idUser, @PathVariable Long currentDate) {
        Optional<Vote> vote = verificationViewVote.verificationViewVote(token, idUser, currentDate);
        if (vote != null) {
            return vote;
        } else {
            return Optional.of(new Vote("the security code has been changed and is no longer valid or the voting is no longer active."));
        }
    }




    @DeleteMapping("/delete/{id_vote}")
    public Information deletVote(@PathVariable("id_vote") Long idVote,
                                 @RequestBody Map<String,String> body){
        Long idUser = Long.valueOf(
                String.valueOf(
                        tokenData.Readclaims(
                                body.get("token")).get("code")));

        serviceVote.deletVote(idVote,idUser);
        return new Information("delete Vote","se elimino la votación correctamente");
    }

    @PostMapping("/create")
    public Information createVote(@RequestBody Map<String,Object> vote){
        serviceVote.CreateVoting(vote);
        return new Information("add Vote","the vote was created correctly");
    }

    @PostMapping("/generate/token/access")
    public String  generateUrl(@RequestBody Map<String,Object> body){
        return tokenData.generateTokenVote(body);
    }

    @GetMapping("/verify/{token}")
    public Map<String, Object> ve(@PathVariable String token){
        return tokenData.verifyDate(token);
    }



}

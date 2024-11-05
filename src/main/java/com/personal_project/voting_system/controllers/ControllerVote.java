package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.dtos.Information;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.security.TokenData;
import com.personal_project.voting_system.services.ServiceUser;
import com.personal_project.voting_system.services.ServiceVote;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/vote")
public class ControllerVote {

    private final ServiceVote serviceVote;
    private final ServiceUser serviceUser;
    private final TokenData tokenData;

    @Autowired
    public ControllerVote(ServiceVote serviceVote, ServiceUser serviceUser, TokenData tokenData) {
        this.serviceVote = serviceVote;
        this.serviceUser = serviceUser;
        this.tokenData = tokenData;
    }


    @GetMapping("/vote/{id}")
    public Vote getVote(@PathVariable Long id){
        return serviceVote.getVote(id);
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
        serviceVote.addVote(new Vote ((String) vote.get("title"),
                serviceUser.getUserById(Long.valueOf(
                        String.valueOf(
                                tokenData.Readclaims(
                                        (String) vote.get("token")).get("code"))))));

        return new Information("add Vote","se creao la votación correctamente");
    }

}

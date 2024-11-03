package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.dtos.Information;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.services.ServiceUser;
import com.personal_project.voting_system.services.ServiceVote;
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
@RequestMapping("/vote")
public class ControllerVote {

    private final ServiceVote serviceVote;
    private final ServiceUser serviceUser;

    @Autowired
    public ControllerVote(ServiceVote serviceVote, ServiceUser serviceUser) {
        this.serviceVote = serviceVote;
        this.serviceUser = serviceUser;
    }

    @GetMapping("/vote/{id}")
    public Vote getVote(@PathVariable Long id){
        return serviceVote.getVote(id);
    }



    @DeleteMapping("/delete/vote/{id_vote}")
    public Information deletVote(@PathVariable("id_vote") Long idVote){
        serviceVote.deletVote(idVote);
        return new Information("delete Vote","se elimino la votación correctamente");
    }

    @PostMapping("/create/vote")
    public Information createvote(@Valid @RequestBody Map<String,Object> body){

        serviceVote.addVote(new Vote((String) body.get("title"),serviceUser.getUser((String) body.get("nameUser"))));

        return new Information("add Vote","se creao la votación correctamente");
    }

}

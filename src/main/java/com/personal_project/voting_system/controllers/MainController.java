package com.personal_project.voting_system.controllers;

import com.personal_project.voting_system.dtos.Information;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.services.Services;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    public MainController(Services services) {
        this.services = services;
    }


    private Services services;

    @GetMapping("/")
    public String getStatus(){
        return "200";
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id){
        return services.getUser(id);
    }

    @GetMapping("/vote/{id}")
    public Vote getVote(@PathVariable Long id){
        return services.getVote(id);
    }

    @SneakyThrows
    @PostMapping("/voted/{id_vote}/option/{id_option}")
    public ResponseEntity<?> voted(@PathVariable("id_option") Long id_option, HttpServletRequest request,@PathVariable("id_vote") Long id_vote){
        String EMOTE_ADDR = request.getRemoteAddr();
        if(services.MatchIP(EMOTE_ADDR,id_vote)){
            return new ResponseEntity<>("You have already voted!", HttpStatus.FORBIDDEN);
        }
        else {
            services.addScore(id_option);
            return new ResponseEntity<>("Vote recorded successfully!", HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/create/vote")
    public Information addvote(@RequestBody Map<String,Object> body){

        services.addVote(new Vote((String) body.get("title"),services.getUser(Long.valueOf((String.valueOf((int) body.get("id_user")))))));

        return new Information("add Vote","se creao la votación correctamente");
    }

    @DeleteMapping("/delete/vote/{id_vote}")
    public Information deletVote(@PathVariable("id_vote") Long idVote){
        services.deletVote(idVote);
        return new Information("delete Vote","se elimino la votación correctamente");
    }
}
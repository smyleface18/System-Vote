package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.Option;
import com.personal_project.voting_system.dtos.Role;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import com.personal_project.voting_system.respository.IRepositoryOption;
import com.personal_project.voting_system.respository.IRepositoryRole;
import com.personal_project.voting_system.respository.IRepositoryUser;
import com.personal_project.voting_system.respository.IRepositoryVote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class Services {

    private final IRepositoryOption iRepositoryOption;
    private final IRepositoryUser iRepositoryUser;
    private final IRepositoryVote iRepositoryVote;
    private final IRepositoryRole iRepositoryRole;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public Services(IRepositoryOption iRepositoryOption, IRepositoryUser iRepositoryUser, IRepositoryVote iRepositoryVote, IRepositoryRole iRepositoryRole, PasswordEncoder passwordEncoder) {
        this.iRepositoryOption = iRepositoryOption;
        this.iRepositoryUser = iRepositoryUser;
        this.iRepositoryVote = iRepositoryVote;
        this.iRepositoryRole = iRepositoryRole;
        this.passwordEncoder = passwordEncoder;
    }


    public User getUser(String name){
        return iRepositoryUser.findByNameUser(name);
    }

    @Transactional
    public User addUser(User user){
        Role roleUser = iRepositoryRole.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        if (roleUser != null){
            roles.add(roleUser);
        }
        else {
            throw new ObjectNotFoundException("ROLE_USER not found");
        }
        if(user.isAdmin()){
            Role roleAdmin = iRepositoryRole.findByName("ROLE_ADMIN");
            if (roleAdmin != null){
                roles.add(roleAdmin);
            }
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        iRepositoryUser.addUser(user);

        return user;
    }

    @Transactional
    public void addVote( Vote vote){
        iRepositoryVote.addVote(vote);
    }

    public Vote getVote(Long id){
        return iRepositoryVote.findByIdVote(id);
    }

    @Transactional
    public void deletVote(Long idVote){
        iRepositoryVote.deletVote(idVote);
    }


    @Transactional
    public void addScore(Long id) throws Exception {
        iRepositoryOption.addScore(id);
    }

    @Transactional
    public void deletOption(Long id){
        iRepositoryOption.deletOption(id);
    }




    @Transactional
    public Boolean MatchIP(String ip, Long id_vote){
       if ((iRepositoryOption.getIPvoter(ip).isEmpty())){

           iRepositoryOption.addIPVoter(ip,iRepositoryVote.findByIdVote(id_vote));
           return false;
       }
       else {
           return true;
       }

    }
}

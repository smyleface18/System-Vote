package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.respository.IRepositoryOption;
import com.personal_project.voting_system.respository.IRepositoryUser;
import com.personal_project.voting_system.respository.IRepositoryVote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Services {

    private final IRepositoryOption iRepositoryOption;
    private final IRepositoryUser iRepositoryUser;
    private final IRepositoryVote iRepositoryVote;

    @Autowired
    public Services(IRepositoryOption iRepositoryOption, IRepositoryUser iRepositoryUser, IRepositoryVote iRepositoryVote) {
        this.iRepositoryOption = iRepositoryOption;
        this.iRepositoryUser = iRepositoryUser;
        this.iRepositoryVote = iRepositoryVote;
    }



    public User getUser(Long id){
        return iRepositoryUser.findByIdUser(id);
    }

    public Vote getVote(Long id){
        return iRepositoryVote.findByIdVote(id);
    }

    public void addScore(Long id) throws Exception {
        iRepositoryOption.addScore(id);
    }

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

package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.respository.IRepositoryUser;
import com.personal_project.voting_system.respository.IRepositoryVote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class ServiceVote {

    private final IRepositoryVote  iRepositoryVote;
    private final IRepositoryUser iRepositoryUser;

    @Autowired
    public ServiceVote(IRepositoryVote iRepositoryVote, IRepositoryUser iRepositoryUser) {
        this.iRepositoryVote = iRepositoryVote;
        this.iRepositoryUser = iRepositoryUser;
    }

    @Transactional
    public void addVote( Vote vote){
        iRepositoryVote.addVote(vote);
    }

    public Vote getVote(Long id){
        return iRepositoryVote.findByIdVote(id);
    }

    @Transactional
    public void deletVote(Long idVote, Long idUser){
      Long idCreator = iRepositoryVote.findByIdVote(idVote).getUser().getId();
      if (Objects.equals(idUser, idCreator)){
          iRepositoryVote.deletVote(idVote);
      }
    }

    @Transactional
    public void registerVoters(Long idUser, Long idVote) {
        iRepositoryVote.registerVoters(idUser,  idVote);
    }
}

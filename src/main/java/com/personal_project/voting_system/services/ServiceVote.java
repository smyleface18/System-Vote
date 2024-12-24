package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.Option;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.respository.IRepositoryOption;
import com.personal_project.voting_system.respository.IRepositoryUser;
import com.personal_project.voting_system.respository.IRepositoryVote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class ServiceVote {

    private final IRepositoryVote  iRepositoryVote;
    private final IRepositoryUser iRepositoryUser;
    private final IRepositoryOption iRepositoryOption;

    @Autowired
    public ServiceVote(IRepositoryOption iRepositoryOption, IRepositoryVote iRepositoryVote, IRepositoryUser iRepositoryUser) {
        this.iRepositoryOption = iRepositoryOption;
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

    @Transactional
    public void CreateVoting(Map<String,Object> vote){
        User creatorUser = iRepositoryUser.findById(Long.valueOf(String.valueOf(vote.get("idUser"))));
        iRepositoryVote.addVote(new Vote ((String) vote.get("title"),
                        creatorUser));

        Optional<Vote> voteOptional = creatorUser.getVote().stream()
                                    .filter(vo -> vo.getTitle().equals(vote.get("title"))).findFirst();

        if(voteOptional.isPresent()){
            Vote newVote = voteOptional.get();
            iRepositoryOption.createOption();
        }

    }
}

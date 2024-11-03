package com.personal_project.voting_system.respository;

import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;

public interface IRepositoryVote {

    Vote findByIdVote(Long id);

    void addVote(Vote vote);
    void deletVote(Long idVote);


    void registerVoters(Long idUser, Long idVote);
}

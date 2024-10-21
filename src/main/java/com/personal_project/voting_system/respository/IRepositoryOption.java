package com.personal_project.voting_system.respository;



import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.dtos.Voters;

import java.util.List;

public interface IRepositoryOption {

    void addScore(Long id) throws Exception;
    void deletOption(Long id);
    List<Voters> getIPvoter(String ip);
    void addIPVoter(String ip, Vote vote);
}

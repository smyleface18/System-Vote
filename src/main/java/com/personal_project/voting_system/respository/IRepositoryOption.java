package com.personal_project.voting_system.respository;


import com.personal_project.voting_system.dtos.Option;

public interface IRepositoryOption {

    void addScore(Long id) throws Exception;
    void deletOption(Long id);
    void createOption(Option option);


}

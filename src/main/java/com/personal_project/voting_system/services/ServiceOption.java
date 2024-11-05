package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.Option;
import com.personal_project.voting_system.respository.IRepositoryOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceOption {

    private IRepositoryOption iRepositoryOption;

    @Autowired
    public ServiceOption(IRepositoryOption iRepositoryOption) {
        this.iRepositoryOption = iRepositoryOption;
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
    public void AddOption(Option option){
        iRepositoryOption.createOption(option);
    }
}

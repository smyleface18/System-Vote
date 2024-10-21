package com.personal_project.voting_system.respository;

import com.personal_project.voting_system.dtos.User;

public interface IRepositoryUser {

    User findByIdUser(Long id);
    void addUser(User user);
}

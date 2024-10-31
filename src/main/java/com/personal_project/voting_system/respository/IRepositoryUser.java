package com.personal_project.voting_system.respository;

import com.personal_project.voting_system.dtos.User;

public interface IRepositoryUser {

    User findByNameUser(String name);
    void addUser(User user);
}

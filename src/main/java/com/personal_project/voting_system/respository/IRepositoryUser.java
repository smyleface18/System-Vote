package com.personal_project.voting_system.respository;

import com.personal_project.voting_system.dtos.User;
import org.springframework.http.ResponseEntity;

public interface IRepositoryUser {

    User findByNameOrEmailUser(String name);
    void addUser(User user);
    Boolean checkExistingName(String name);
    Boolean checkExistingEmail(String name);
}

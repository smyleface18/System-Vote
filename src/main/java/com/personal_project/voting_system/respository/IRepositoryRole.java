package com.personal_project.voting_system.respository;

import com.personal_project.voting_system.dtos.Role;


import java.util.Optional;

public interface IRepositoryRole {

    Role findByName(String name);

}

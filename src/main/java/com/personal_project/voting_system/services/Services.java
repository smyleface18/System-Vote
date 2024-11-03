package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.*;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import com.personal_project.voting_system.respository.IRepositoryOption;
import com.personal_project.voting_system.respository.IRepositoryRole;
import com.personal_project.voting_system.respository.IRepositoryUser;
import com.personal_project.voting_system.respository.IRepositoryVote;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class Services {

    private final IRepositoryUser iRepositoryUser;
    private final IRepositoryVote iRepositoryVote;
    private final IRepositoryRole iRepositoryRole;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public Services( IRepositoryUser iRepositoryUser, IRepositoryVote iRepositoryVote, IRepositoryRole iRepositoryRole, PasswordEncoder passwordEncoder) {
        this.iRepositoryUser = iRepositoryUser;
        this.iRepositoryVote = iRepositoryVote;
        this.iRepositoryRole = iRepositoryRole;
        this.passwordEncoder = passwordEncoder;
    }
















}

package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.ErrorApp;
import com.personal_project.voting_system.dtos.Role;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import com.personal_project.voting_system.respository.IRepositoryRole;
import com.personal_project.voting_system.respository.IRepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServiceUser {

    private final IRepositoryUser iRepositoryUser;
    private final IRepositoryRole iRepositoryRole;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public ServiceUser(IRepositoryUser iRepositoryUser, IRepositoryRole iRepositoryRole, PasswordEncoder passwordEncoder) {
        this.iRepositoryUser = iRepositoryUser;
        this.iRepositoryRole = iRepositoryRole;
        this.passwordEncoder = passwordEncoder;
    }



    public User getUser(String name){
        return iRepositoryUser.findByNameOrEmailUser(name);
    }

    @Transactional
    public ResponseEntity<?> addUser(User user) throws ObjectNotFoundException {
        try {
            Role roleUser = iRepositoryRole.findByName("ROLE_USER");
            List<Role> roles = new ArrayList<>();
            if (roleUser != null){
                roles.add(roleUser);
            }
            else {
                throw new ObjectNotFoundException("ROLE_USER not found");
            }
            if(user.isAdmin()){
                Role roleAdmin = iRepositoryRole.findByName("ROLE_ADMIN");
                if (roleAdmin != null){
                    roles.add(roleAdmin);
                }
            }
            user.setRoles(roles);
            if(iRepositoryUser.checkExistingName(user.getName()) ){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(new ErrorApp("Ese nombre de usuario ya esta registrado por favor ingrese otro nombre",
                                HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date()));
            }
            if (iRepositoryUser.checkExistingEmail(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(new ErrorApp("Ese correo ya esta registrado por favor ingrese otro correo",
                                HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date()));
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            iRepositoryUser.addUser(user);
        }
        catch(ObjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorApp("no se pudo encontrar el role",HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}

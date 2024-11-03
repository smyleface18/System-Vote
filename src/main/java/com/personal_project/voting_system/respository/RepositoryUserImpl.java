package com.personal_project.voting_system.respository;

import com.personal_project.voting_system.dtos.ErrorApp;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public class RepositoryUserImpl implements IRepositoryUser{

    @PersistenceContext
    EntityManager entityManager;


    @Transactional
    public User findByNameOrEmailUser(String username) {
        String query = "SELECT u FROM User u WHERE u.name = :username or u.email = :username";
        User user = (User) entityManager.createQuery(query)
                .setParameter("username",username)
                .getSingleResult();
        if (user == null) {
            throw new ObjectNotFoundException("No se encontró un usuario con el nombre o email:".concat(username));
        }
        return user;
    }


    public void addUser(User user) {
            entityManager.merge(user);
    }

    @Override
    public Boolean checkExistingName(String name) {
        boolean value = false;
        String query = "SELECT u FROM User u WHERE u.name = :name";
        List<User> listNames = entityManager.createQuery(query).setParameter("name", name).getResultList();
        if(!listNames.isEmpty()){
            value = true;
        }
        return value ;
    }

    @Override
    public Boolean checkExistingEmail( String emial) {
        boolean value = false;
        String query = "SELECT u FROM User u WHERE u.email = :email";
        List<User> listEmails =entityManager.createQuery(query).setParameter("email",emial).getResultList();
        if(!listEmails.isEmpty()){
            value = true;
        }
        return value ;
    }
}

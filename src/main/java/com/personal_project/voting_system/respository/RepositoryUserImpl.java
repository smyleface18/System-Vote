package com.personal_project.voting_system.respository;

import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RepositoryUserImpl implements IRepositoryUser{

    @PersistenceContext
    EntityManager entityManager;


    @Transactional
    public User findByIdUser(Long id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new ObjectNotFoundException("No se encontró un usuario con el id: " + id);
        }
        return user;
    }

    @Transactional
    public  void addUser(User user) {
        entityManager.merge(user);
    }
}

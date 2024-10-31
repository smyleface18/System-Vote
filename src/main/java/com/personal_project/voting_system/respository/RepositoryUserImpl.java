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
    public User findByNameUser(String name) {
        String query = "SELECT u FROM User u WHERE u.name = :name";
        User user = (User) entityManager.createQuery(query)
                .setParameter("name",name)
                .getSingleResult();
        if (user == null) {
            throw new ObjectNotFoundException("No se encontró un usuario con el name:".concat(name));
        }
        return user;
    }

    @Transactional
    public  void addUser(User user) {
        entityManager.merge(user);
    }
}

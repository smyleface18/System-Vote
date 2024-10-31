package com.personal_project.voting_system.respository;

import com.personal_project.voting_system.dtos.Role;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositoryRoleImpl implements IRepositoryRole {

    private EntityManager entityManager;

    @Autowired
    public RepositoryRoleImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findByName(String name) {
        String query = "SELECT r FROM Role r WHERE r.name = :name";
        return (Role) entityManager.createQuery(query)
                .setParameter("name", name)
                .getSingleResult();
    }

}

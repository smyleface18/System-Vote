package com.personal_project.voting_system.respository;

import com.personal_project.voting_system.dtos.Option;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RepositoryOptionImpl implements IRepositoryOption{

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public void addScore(Long id)  {
        Option op = entityManager.find(Option.class, id);
        if (op == null) {
            throw new ObjectNotFoundException("No se encontró una opcion con el id: " + id);
        }
        else {
            op.setVotes(op.getVotes()+1);
            entityManager.merge(op);
        }
    }

    @Transactional
    @Override
    public void deletOption(Long id) {
        Option op = entityManager.find(Option.class, id);
        if (op == null) {
            throw new ObjectNotFoundException("No se encontró una opcion con el id: " + id);
        }
        else {
            entityManager.remove(op);
        }
    }



}

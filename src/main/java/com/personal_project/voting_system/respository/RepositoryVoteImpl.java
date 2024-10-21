package com.personal_project.voting_system.respository;

import com.personal_project.voting_system.dtos.Option;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RepositoryVoteImpl implements IRepositoryVote{

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public Vote findByIdVote(Long id) {
        Vote vote = entityManager.find(Vote.class, id);
        if (vote == null) {
            throw new ObjectNotFoundException("No se encontró la votacion con el id: " + id);
        }
        return vote;
    }
}

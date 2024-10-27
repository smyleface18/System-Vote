package com.personal_project.voting_system.respository;


import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;


@Repository
public class RepositoryVoteImpl implements IRepositoryVote{

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public Vote findByIdVote(Long id) {
        Vote vote = entityManager.find(Vote.class, id);
        if (vote == null) {
            throw new ObjectNotFoundException("No se encontró la votacion con el id: " + id);
        }
        return vote;
    }

    @Override
    public void addVote(Vote vote){
        entityManager.merge(vote);
    }

    @Override
    public void deletVote(Long idVote){
        Vote vote =  entityManager.find(Vote.class,idVote);
        if (vote == null){
            throw new ObjectNotFoundException("No se encontró una votacion con el id: " + idVote);
        }
        else {
            entityManager.remove(vote);
        }
    }
}

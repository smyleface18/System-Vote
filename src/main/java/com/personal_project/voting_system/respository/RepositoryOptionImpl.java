package com.personal_project.voting_system.respository;

import com.personal_project.voting_system.dtos.Option;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.dtos.Voters;
import com.personal_project.voting_system.exceptions.ObjectNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    @Override
    @Transactional
    public List<Voters>  getIPvoter(String ip) {
            String query = "SELECT i FROM Voters i WHERE i.ip = :ip";
            return entityManager.createQuery(query, Voters.class)
                    .setParameter("ip",ip)
                    .getResultList();

    }

    @Override
    public void addIPVoter(String ip, Vote vote){
        entityManager.merge(new Voters(ip,vote));
    }
}

package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.Option;
import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.respository.IRepositoryOption;
import com.personal_project.voting_system.respository.IRepositoryUser;
import com.personal_project.voting_system.respository.IRepositoryVote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ServiceVote {

    private static final Logger log = LoggerFactory.getLogger(ServiceVote.class);
    private final IRepositoryVote  iRepositoryVote;
    private final IRepositoryUser iRepositoryUser;
    private final IRepositoryOption iRepositoryOption;

    @Autowired
    public ServiceVote(IRepositoryOption iRepositoryOption, IRepositoryVote iRepositoryVote, IRepositoryUser iRepositoryUser) {
        this.iRepositoryOption = iRepositoryOption;
        this.iRepositoryVote = iRepositoryVote;
        this.iRepositoryUser = iRepositoryUser;
    }

    @Transactional
    public void addVote( Vote vote){
        iRepositoryVote.addVote(vote);
    }

    public Vote getVote(Long id){
        return iRepositoryVote.findByIdVote(id);
    }

    @Transactional
    public void deletVote(Long idVote, Long idUser){
      Long idCreator = iRepositoryVote.findByIdVote(idVote).getUser().getId();
      if (Objects.equals(idUser, idCreator)){
          iRepositoryVote.deletVote(idVote);
      }
    }

    @Transactional
    public void registerVoters(Long idUser, Long idVote) {
        iRepositoryVote.registerVoters(idUser,  idVote);
    }

    @Transactional
    public void CreateVoting(Map<String,Object> map){
        User creatorUser = iRepositoryUser.findById(Long.valueOf(String.valueOf(map.get("idUser"))));
        iRepositoryVote.addVote(new Vote ((String) map.get("title"),
                        creatorUser));

        Optional<Vote> voteOptional = creatorUser.getVote().stream()
                                    .filter(vo -> vo.getTitle().equals(map.get("title"))).findFirst();

        if(voteOptional.isPresent()){
            Vote newVote = voteOptional.get();
            String dateInit = String.valueOf(map.get("dateInit"));
            String dateEnd = String.valueOf(map.get("dateEnd"));
            String regex = String.valueOf(map.get("regex"));
            if (dateInit != null && !dateInit.equals("0") && dateEnd != null && !dateEnd.equals("0")) {
                newVote.setDateInit(dateInit);
                newVote.setDateEnd(dateEnd);
            }
            if (regex != null && !regex.equals("")){
                newVote.setRegex(regex);
            }

            Object objectOptions = map.get("options");
            if(objectOptions instanceof List){
                List<String> optionsList = (List<String>) objectOptions;
                ArrayList<String> arrayList = new ArrayList<>((List<String>) optionsList);
                arrayList.forEach(option ->  iRepositoryOption.createOption(new Option(option, newVote)));
                log.info("creo que bien jajaj :D ssssssssss");
            }

        }

    }
}

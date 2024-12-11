package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.security.TokenData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VerificationViewVote {

    private final TokenData tokenData;
    private final ServiceVote serviceVote;
    private final ServiceUser serviceUser;

    @Autowired
    public VerificationViewVote(TokenData tokenData, ServiceVote serviceVote, ServiceUser serviceUser) {
        this.tokenData = tokenData;
        this.serviceVote = serviceVote;
        this.serviceUser = serviceUser;
    }

    public Vote verificationViewVote(String token, Long idUser){
        Map<String,Object> map = tokenData.Readclaims(token);
        if (new Date().after(new Date((Long) map.get("dateInitial"))) && new Date().before(new Date((Long)map.get("dateEnd"))) ){
            Vote vote = serviceVote.getVote(Long.valueOf(String.valueOf(map.get("idVote"))));
            User user = serviceUser.getUserById(idUser);
            Pattern pattern = Pattern.compile(".*"+vote.getRegex());
            Matcher matcher = pattern.matcher(user.getEmail());
            if (matcher.matches()){
                return vote;
            }
        }
        return null;
    }
}

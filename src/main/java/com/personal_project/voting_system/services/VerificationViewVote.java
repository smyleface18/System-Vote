package com.personal_project.voting_system.services;

import com.personal_project.voting_system.dtos.User;
import com.personal_project.voting_system.dtos.Vote;
import com.personal_project.voting_system.security.TokenData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
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

    public Optional<Vote> verificationViewVote(String token, Long idUser, Long currentDate) {
        Map<String, Object> map = tokenData.Readclaims(token);
        Vote vote = serviceVote.getVote(Long.valueOf(String.valueOf(map.get("idVote"))));
        User user = serviceUser.getUserById(idUser);
        boolean havedDate = !String.valueOf(vote.getDateEnd()).equals("null");
        boolean actived = false;
        if (havedDate) {
            Date dateInit = new Date(Long.parseLong(vote.getDateInit()));
            Date dateEnd = new Date(Long.parseLong(vote.getDateEnd()));
            Date current = new Date(currentDate);
            actived = actived(dateInit, dateEnd, current);
        }

        if (vote.getRegex() != null && !vote.getRegex().equals("null") && !vote.getRegex().isEmpty()) {
            Pattern pattern = Pattern.compile(".*" + vote.getRegex());
            Matcher matcher = pattern.matcher(user.getEmail());

            if (matcher.matches()) {
                if (!havedDate) {
                    return Optional.of(vote);
                } else if (actived) {
                    return Optional.of(vote);
                }
            } else {
                return null;
            }
        } else {
            if (!havedDate) {
                return Optional.of(vote);
            } else if (actived) {
                return Optional.of(vote);
            }
        }
        return null;
    }


    public static boolean actived(Date dateInit, Date dateEnd, Date current) {
        return current.after(dateInit) && current.before(dateEnd);
    }
}

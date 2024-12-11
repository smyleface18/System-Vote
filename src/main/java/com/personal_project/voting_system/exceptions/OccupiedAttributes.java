package com.personal_project.voting_system.exceptions;

import com.personal_project.voting_system.Enums.IconsAlerts;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;


@Getter
public class OccupiedAttributes  extends RuntimeException {

    private String title;
    private String icon;

    public OccupiedAttributes(String title, String message) {
        super(message);
        this.title = title;
        this.icon = "error";
    }

}

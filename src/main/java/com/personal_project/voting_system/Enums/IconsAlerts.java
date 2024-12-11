package com.personal_project.voting_system.Enums;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Getter
public class IconsAlerts {

    private  String SUCCESS ;
    private  String ERROR;
    private  String WARNING;
    private  String INFO ;
    private  String QUESTION;

    public IconsAlerts() {
        this.SUCCESS = "success";
        this.ERROR = "error";
        this.WARNING = "warning";
        this.INFO = "info";
        this.QUESTION = "question";
    }
}

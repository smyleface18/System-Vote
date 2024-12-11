package com.personal_project.voting_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Information {

    private String title;
    private String message;
    private String icon;

    public Information(String title, String message) {
        this.title = title;
        this.message = message;
    }
}

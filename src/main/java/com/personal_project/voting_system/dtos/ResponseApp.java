package com.personal_project.voting_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseApp {

    private String title;
    private String icon;
    private String msg;
    private int status;
    private Date date;

    public ResponseApp(String title, String icon, int status, Date date) {
        this.title = title;
        this.icon = icon;
        this.status = status;
        this.date = date;
    }

    public ResponseApp(String title, int status, Date date) {
        this.title = title;
        this.status = status;
        this.date = date;
    }
}

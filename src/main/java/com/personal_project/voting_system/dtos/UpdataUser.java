package com.personal_project.voting_system.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdataUser {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String newPassword;


}

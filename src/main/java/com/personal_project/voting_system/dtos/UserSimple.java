package com.personal_project.voting_system.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "user")
@Entity
@Getter
@Setter
public class UserSimple {

    @Id
    @Column(name = "id_user")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "img")
    private String img;
}

package com.personal_project.voting_system.dtos;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Table(name = "user")
@Entity
@Getter
@Setter
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(min = 8)
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user","voters"})
    private List<Vote> vote;

    @Transient
    private boolean admin;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "id_user"),
                inverseJoinColumns = @JoinColumn(name = "id_roles"),
                uniqueConstraints = {@UniqueConstraint(columnNames = {"id_roles","id_user"})}
    )
    private List<Role> roles;


    @ManyToMany(
            mappedBy = "voters"
    )
   @JsonIgnoreProperties({"user", "voters"})
    private List<Vote> voted;


    @Column(name = "img")
    private String img;
}

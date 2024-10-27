package com.personal_project.voting_system.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "vote")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vote")
    @Getter @Setter
    private Long id;

    @Column(name = "title")
    @Getter @Setter
    private String tile;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Getter @Setter
    private List<Option> options;


    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Getter @Setter
    private List<Voters> voters;


    public Vote(String tile, User user) {
        this.tile = tile;
        this.user = user;
    }

    public Vote(Long id, String tile, User user) {
        this.id = id;
        this.tile = tile;
        this.user = user;
    }
}

package com.personal_project.voting_system.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "voters")
@Entity
public class Voters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voters")
    @Getter @Setter
    private Long id;

    @Column(name = "ip_voters")
    @Getter @Setter
    private String ip;

    @ManyToOne
    @JoinColumn(name = "id_vote", nullable = false)
    @Getter @Setter
    @JsonBackReference
    private Vote vote;

    public Voters(String ip, Vote vote) {
        this.ip = ip;
        this.vote = vote;
    }

    public Voters() {
    }
}

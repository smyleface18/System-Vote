package com.personal_project.voting_system.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "option")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Option {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_option")
        @Getter @Setter
        private Long id;

        @Column(name = "text")
        @Getter @Setter
        private String text;

        @Column(name = "url")
        @Getter @Setter
        private String img;

        @Column(name = "votes")
        @Getter @Setter
        private Integer votes;


        @ManyToOne
        @JoinColumn(name = "id_vote", nullable = false)
        @JsonBackReference
        private Vote vote;

        public Option(String text, Vote vote) {
                this.text = text;
                this.vote = vote;
                this.votes = 0;
        }
}

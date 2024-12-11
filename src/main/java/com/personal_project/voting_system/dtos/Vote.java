package com.personal_project.voting_system.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String title;

    @Column(name = "regex")
    @Getter @Setter
    private String regex;

    @Column(name = "date_init")
    @Getter @Setter
    private String dateInit;

    @Column(name = "date_end")
    @Getter @Setter
    private String dateEnd;


    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    @JsonIgnoreProperties({"vote"})
    @Getter
    private User user;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Getter @Setter
    private List<Option> options;


    @ManyToMany @JoinTable( name = "users_votes",
            joinColumns = @JoinColumn(name = "id_vote"),
            inverseJoinColumns = @JoinColumn(name = "id_user") )
    @JsonIgnoreProperties("voted")
    @Getter @Setter
    private List<User> voters;


    public Vote(String title, User user) {
        this.title = title;
        this.user = user;
    }

    public Vote(Long id, String tile, User user) {
        this.id = id;
        this.title = tile;
        this.user = user;
    }

    public Vote(String title, User user, String regex, String dateInit, String dateEnd) {
        this.title = title;
        this.user = user;
        this.regex = regex;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
    }
}

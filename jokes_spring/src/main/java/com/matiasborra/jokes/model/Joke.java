package com.matiasborra.jokes.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "jokes")
public class Joke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text1", nullable = false)
    private String text1;

    @Column(name = "text2")
    private String text2;

    // Relación Many-to-Many con flags
    @ManyToMany
    @JoinTable(
        name = "jokes_flags",
        joinColumns = @JoinColumn(name = "joke_id"),
        inverseJoinColumns = @JoinColumn(name = "flag_id")
    )
    private Set<Flag> flags;

}
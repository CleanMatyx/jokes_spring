package com.matiasborra.jokes.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "flags")
@Data
public class Flag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flag", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "flags")
    private Set<Joke> jokes;

}
package com.matiasborra.jokes.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flags")
public class Flag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flag;

    @OneToMany(mappedBy = "flag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JokeFlag> jokeFlags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Set<JokeFlag> getJokeFlags() {
        return jokeFlags;
    }

    public void setJokeFlags(Set<JokeFlag> jokeFlags) {
        this.jokeFlags = jokeFlags;
    }
}
package com.matiasborra.jokes.model.entity;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "jokes_flags", schema = "public")
public class JokeFlag implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "joke_id", nullable = false)
    private Long jokeId;

    @Id
    @Column(name = "flag_id", nullable = false)
    private Long flagId;

    public JokeFlag() {
    }

    public JokeFlag(Long jokeId, Long flagId) {
        this.jokeId = jokeId;
        this.flagId = flagId;
    }

    public Long getJokeId() {
        return jokeId;
    }

    public void setJokeId(Long jokeId) {
        this.jokeId = jokeId;
    }

    public Long getFlagId() {
        return flagId;
    }

    public void setFlagId(Long flagId) {
        this.flagId = flagId;
    }
}
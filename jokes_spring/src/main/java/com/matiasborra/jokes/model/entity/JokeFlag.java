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
    private Integer jokeId;

    @Id
    @Column(name = "flag_id", nullable = false)
    private Integer flagId;

    public JokeFlag() {
    }

    public JokeFlag(Integer jokeId, Integer flagId) {
        this.jokeId = jokeId;
        this.flagId = flagId;
    }

    public Integer getJokeId() {
        return jokeId;
    }

    public void setJokeId(Integer jokeId) {
        this.jokeId = jokeId;
    }

    public Integer getFlagId() {
        return flagId;
    }

    public void setFlagId(Integer flagId) {
        this.flagId = flagId;
    }
}
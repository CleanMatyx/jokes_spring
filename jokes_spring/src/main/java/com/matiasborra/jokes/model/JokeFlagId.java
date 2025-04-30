package com.matiasborra.jokes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
public class JokeFlagId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "joke_id")
    private Long jokeId;

    @Column(name = "flag_id")
    private Long flagId;

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
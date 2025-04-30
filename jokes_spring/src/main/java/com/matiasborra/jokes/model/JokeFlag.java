package com.matiasborra.jokes.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "jokes_flags")
public class JokeFlag {

    @EmbeddedId
    private JokeFlagId id;

    @ManyToOne
    @MapsId("jokeId")
    @JoinColumn(name = "joke_id")
    private Joke joke;

    @ManyToOne
    @MapsId("flagId")
    @JoinColumn(name = "flag_id")
    private Flag flag;

    public JokeFlagId getId() {
        return id;
    }

    public void setId(JokeFlagId id) {
        this.id = id;
    }

    public Joke getJoke() {
        return joke;
    }

    public void setJoke(Joke joke) {
        this.joke = joke;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }
}
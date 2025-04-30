package com.matiasborra.jokes.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "joke_flag")
public class JokeFlag {

    @EmbeddedId
    private JokeFlagId id = new JokeFlagId();  // ← inicializa aquí

    @MapsId("jokeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "joke_id")
    private Joke joke;

    @MapsId("flagId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flag_id")
    private Flag flag;

    // al usar Lombok @Data te genera setters automáticos,
    // pero para asegurarte de que el id se sincronice,
    // puedes hacer manualmente estos setters:
    public void setJoke(Joke joke) {
        this.joke = joke;
        this.id.setJokeId(joke.getId());
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
        this.id.setFlagId(flag.getId());
    }

    public JokeFlagId getId() {
        return id;
    }

    public void setId(JokeFlagId id) {
        this.id = id;
    }

    public Joke getJoke() {
        return joke;
    }

    public Flag getFlag() {
        return flag;
    }
}

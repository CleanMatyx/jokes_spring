package com.matiasborra.jokes.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "jokes_flags")
@Data
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
}
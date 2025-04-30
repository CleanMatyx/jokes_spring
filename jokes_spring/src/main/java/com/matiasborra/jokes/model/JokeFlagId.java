package com.matiasborra.jokes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class JokeFlagId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "joke_id")
    private Long jokeId;

    @Column(name = "flag_id")
    private Long flagId;
}
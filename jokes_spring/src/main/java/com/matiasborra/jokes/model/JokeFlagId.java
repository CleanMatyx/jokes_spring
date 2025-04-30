package com.matiasborra.jokes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JokeFlagId implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "joke_id")
    private Long jokeId;
    @Column(name = "flag_id")
    private Long flagId;
    @Override
    public int hashCode() {
        return Objects.hash(flagId, jokeId);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JokeFlagId other = (JokeFlagId) obj;
        return Objects.equals(flagId, other.flagId) && Objects.equals(jokeId, other.jokeId);
    }

    @Override
    public String toString() {
        return "JokeFlagId [jokeId=" + jokeId + ", flagId=" + flagId + "]";
    }
}
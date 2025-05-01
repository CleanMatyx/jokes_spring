package com.matiasborra.jokes.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "language", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String language) {
        this.name = language;
    }
}
package com.matiasborra.jokes.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "jokes")
public class Joke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text1", nullable = false)
    private String text1;

    @Column(name = "text2")
    private String text2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @OneToMany(mappedBy = "joke", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JokeFlag> flags = new HashSet<>();

    // additional helper methods if needed
    public void addFlag(JokeFlag jokeFlag) {
        flags.add(jokeFlag);
        jokeFlag.setJoke(this);
    }

    public void removeFlag(JokeFlag jokeFlag) {
        flags.remove(jokeFlag);
        jokeFlag.setJoke(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<JokeFlag> getFlags() {
        return flags;
    }

    public void setFlags(Set<JokeFlag> flags) {
        this.flags = flags;
    }
}
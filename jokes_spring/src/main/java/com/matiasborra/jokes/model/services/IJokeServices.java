package com.matiasborra.jokes.model.services;

import com.matiasborra.jokes.model.entity.*;

import java.util.List;

public interface IJokeServices {
    public List<Joke> findAll();
    public Joke findById(Long id);
    public Joke create(Joke joke);
    public Joke update(Long id, Joke joke);
    public void delete(Long id);
    public List<Category> findAllCategories();
    public List<Type> findAllTypes();
    public List<Language> findAllLanguages();
    public List<Flag> findAllFlags();
}
package com.matiasborra.jokes.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.matiasborra.jokes.model.entity.Joke;

public interface JokeDAO extends JpaRepository<Joke, Long> { }
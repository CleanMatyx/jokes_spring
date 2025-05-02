package com.matiasborra.jokes.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.matiasborra.jokes.model.entity.Joke;
import org.springframework.stereotype.Repository;

@Repository
public interface IJokeDAO extends JpaRepository<Joke, Long> { }
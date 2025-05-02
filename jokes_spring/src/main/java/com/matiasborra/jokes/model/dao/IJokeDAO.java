package com.matiasborra.jokes.model.dao;

import com.matiasborra.jokes.model.entity.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJokeDAO extends JpaRepository<Joke, Long> { }
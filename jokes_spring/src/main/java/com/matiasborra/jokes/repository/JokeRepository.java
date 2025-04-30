package com.matiasborra.jokes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.matiasborra.jokes.model.Joke;

public interface JokeRepository extends JpaRepository<Joke, Long> { }
package com.matiasborra.jokes.repository;

import com.matiasborra.jokes.model.JokeFlag;
import com.matiasborra.jokes.model.JokeFlagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JokeFlagRepository extends JpaRepository<JokeFlag, JokeFlagId> { }
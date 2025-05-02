package com.matiasborra.jokes.model.dao;

import com.matiasborra.jokes.model.entity.JokeFlag;
import com.matiasborra.jokes.model.entity.JokeFlagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJokeFlagDAO extends JpaRepository<JokeFlag, JokeFlagId> { }
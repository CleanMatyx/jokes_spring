package com.matiasborra.jokes.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.matiasborra.jokes.model.entity.Flag;

public interface IFlagDAO extends JpaRepository<Flag, Long> { }

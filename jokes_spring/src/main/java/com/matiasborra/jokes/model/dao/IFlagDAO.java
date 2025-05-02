package com.matiasborra.jokes.model.dao;

import com.matiasborra.jokes.model.entity.Flag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFlagDAO extends JpaRepository<Flag, Long> { }

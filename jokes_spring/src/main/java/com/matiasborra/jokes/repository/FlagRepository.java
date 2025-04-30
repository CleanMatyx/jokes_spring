package com.matiasborra.jokes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.matiasborra.jokes.model.Flag;

public interface FlagRepository extends JpaRepository<Flag, Long> { }

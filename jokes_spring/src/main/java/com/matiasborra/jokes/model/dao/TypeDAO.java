package com.matiasborra.jokes.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.matiasborra.jokes.model.entity.Type;

@Repository
public interface TypeDAO extends JpaRepository<Type, Long> {}

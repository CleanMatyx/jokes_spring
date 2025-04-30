package com.matiasborra.jokes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.matiasborra.jokes.model.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {}

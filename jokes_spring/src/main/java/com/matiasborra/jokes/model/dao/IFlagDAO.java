package com.matiasborra.jokes.model.dao;

import com.matiasborra.jokes.model.entity.Flag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IFlagDAO extends JpaRepository<Flag, Long> {
    @Query("SELECT f FROM Flag f WHERE f.name = :name")
    Optional<Flag> findByName(@Param("name") String name);
}

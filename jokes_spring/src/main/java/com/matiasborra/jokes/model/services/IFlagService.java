package com.matiasborra.jokes.model.services;

import com.matiasborra.jokes.model.entity.Flag;

import java.util.List;
import java.util.Optional;

public interface IFlagService {
    List<Flag> findAllFlags();
    Optional<Flag> findFlagById(Long id);
    Flag save(Flag flag);
    void deleteById(Long id);

}
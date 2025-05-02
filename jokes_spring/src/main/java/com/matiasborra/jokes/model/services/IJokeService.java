package com.matiasborra.jokes.model.services;

import com.matiasborra.jokes.dto.*;
import com.matiasborra.jokes.model.entity.*;

import java.util.List;
import java.util.Optional;

public interface IJokeService {
    public List<JokeDto> findAll();
    public JokeDto findById(Long id);
    public JokeDto create(Joke joke);
    public JokeDto update(Long id, Joke joke);
    public void delete(Long id);
    public List<CategoryDto> findAllCategories();
    public List<TypeDto> findAllTypes();
    public List<LanguageDto> findAllLanguages();
    public List<FlagDto> findAllFlags();
    Optional<FlagDto> findFlagById(Long id);
}
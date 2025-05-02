package com.matiasborra.jokes.model.services;

import com.matiasborra.jokes.dto.CategoryDto;
import com.matiasborra.jokes.dto.CreateJokeDto;
import com.matiasborra.jokes.dto.FlagDto;
import com.matiasborra.jokes.dto.JokeDto;
import com.matiasborra.jokes.dto.LanguageDto;
import com.matiasborra.jokes.dto.TypeDto;
import com.matiasborra.jokes.model.entity.Joke;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IJokeService {
    List<JokeDto> findAll();
    Joke findById(Long id);
    JokeDto create(CreateJokeDto dto);
    JokeDto update(Long id, CreateJokeDto dto);

    @Transactional
    Joke create(Joke in);

    void delete(Long id);
    List<CategoryDto> findAllCategories();
    List<TypeDto> findAllTypes();
    List<LanguageDto> findAllLanguages();
    List<FlagDto> findAllFlags();
    Optional<FlagDto> findFlagById(Long id);
}

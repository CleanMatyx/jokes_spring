package com.matiasborra.jokes.mapper;

import com.matiasborra.jokes.dto.JokeDto;
import com.matiasborra.jokes.model.entity.Joke;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JokeMapper {
    JokeDto toDto(Joke joke);
}

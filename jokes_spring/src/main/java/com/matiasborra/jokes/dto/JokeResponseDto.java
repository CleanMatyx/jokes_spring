package com.matiasborra.jokes.dto;

import java.util.List;

public class JokeResponseDto {
    private Long id;
    private String text1;
    private String text2;
    private CategoryDto category;
    private TypeDto type;
    private LanguageDto language;
    private List<FlagDto> flags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public TypeDto getType() {
        return type;
    }

    public void setType(TypeDto type) {
        this.type = type;
    }

    public LanguageDto getLanguage() {
        return language;
    }

    public void setLanguage(LanguageDto language) {
        this.language = language;
    }

    public List<FlagDto> getFlags() {
        return flags;
    }

    public void setFlags(List<FlagDto> flags) {
        this.flags = flags;
    }
}

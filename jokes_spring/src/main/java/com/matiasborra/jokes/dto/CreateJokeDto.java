package com.matiasborra.jokes.dto;

import java.util.List;

public class CreateJokeDto {
    private Long id;
    private Long categoryId;
    private Long typeId;
    private Long languageId;
    private String text1;
    private String text2;
    private List<Long> flagIds;

    // id
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // categoría
    public Long getCategory() { return categoryId; }
    public void setCategory(Long categoryId) { this.categoryId = categoryId; }

    // tipo
    public Long getType() { return typeId; }
    public void setType(Long typeId) { this.typeId = typeId; }

    // lenguaje
    public Long getLanguage() { return languageId; }
    public void setLanguage(Long languageId) { this.languageId = languageId; }

    // textos
    public String getText1() { return text1; }
    public void setText1(String text1) { this.text1 = text1; }
    public String getText2() { return text2; }
    public void setText2(String text2) { this.text2 = text2; }

    // flags
    public List<Long> getFlags() { return flagIds; }
    public void setFlags(List<Long> flagIds) { this.flagIds = flagIds; }
}

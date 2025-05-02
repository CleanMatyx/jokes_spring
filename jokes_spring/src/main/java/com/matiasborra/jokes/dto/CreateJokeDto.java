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
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    // tipo
    public Long getTypeId() { return typeId; }
    public void setTypeId(Long typeId) { this.typeId = typeId; }

    // lenguaje
    public Long getLanguageId() { return languageId; }
    public void setLanguageId(Long languageId) { this.languageId = languageId; }

    // textos
    public String getText1() { return text1; }
    public void setText1(String text1) { this.text1 = text1; }
    public String getText2() { return text2; }
    public void setText2(String text2) { this.text2 = text2; }

    // flags
    public List<Long> getFlagIds() { return flagIds; }
    public void setFlagIds(List<Long> flagIds) { this.flagIds = flagIds; }
}

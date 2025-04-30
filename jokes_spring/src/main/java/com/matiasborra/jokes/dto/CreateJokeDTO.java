package com.matiasborra.jokes.dto;

import java.util.List;

public class CreateJokeDTO {
    private NestedId category;
    private NestedId type;
    private NestedId language;
    private String text1;
    private String text2;
    private List<NestedId> flags;

    // getters / setters

    public NestedId getCategory() { return category; }
    public void setCategory(NestedId category) { this.category = category; }

    public NestedId getType() { return type; }
    public void setType(NestedId type) { this.type = type; }

    public NestedId getLanguage() { return language; }
    public void setLanguage(NestedId language) { this.language = language; }

    public String getText1() { return text1; }
    public void setText1(String text1) { this.text1 = text1; }

    public String getText2() { return text2; }
    public void setText2(String text2) { this.text2 = text2; }

    public List<NestedId> getFlags() { return flags; }
    public void setFlags(List<NestedId> flags) { this.flags = flags; }

    /** Clase interna que sólo contiene un id */
    public static class NestedId {
        private Long id;
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
    }
}

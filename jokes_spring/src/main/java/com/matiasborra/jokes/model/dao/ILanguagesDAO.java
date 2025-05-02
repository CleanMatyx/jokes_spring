package com.matiasborra.jokes.model.dao;

import com.matiasborra.jokes.model.entity.Languages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILanguagesDAO extends JpaRepository<Languages, Long> { }
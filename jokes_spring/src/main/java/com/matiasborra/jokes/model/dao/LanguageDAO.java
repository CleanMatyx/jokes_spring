package com.matiasborra.jokes.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.matiasborra.jokes.model.entity.Language;

@Repository
public interface LanguageDAO extends JpaRepository<Language, Long> {}

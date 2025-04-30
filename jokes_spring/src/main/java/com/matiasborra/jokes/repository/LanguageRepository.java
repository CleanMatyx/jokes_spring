package com.matiasborra.jokes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.matiasborra.jokes.model.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {}

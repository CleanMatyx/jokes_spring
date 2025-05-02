package com.matiasborra.jokes.model.dao;

import com.matiasborra.jokes.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryDAO extends JpaRepository<Category, Long> {}

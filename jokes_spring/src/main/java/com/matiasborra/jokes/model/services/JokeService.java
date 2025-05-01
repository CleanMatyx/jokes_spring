package com.matiasborra.jokes.model.services;

import com.matiasborra.jokes.dto.CreateJokeDTO;
import com.matiasborra.jokes.model.entity.Category;
import com.matiasborra.jokes.model.entity.Flag;
import com.matiasborra.jokes.model.entity.Joke;
import com.matiasborra.jokes.model.entity.JokeFlag;
import com.matiasborra.jokes.model.entity.Language;
import com.matiasborra.jokes.model.entity.Type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JokeService {

    @PersistenceContext
    private EntityManager em;

    public Joke findById(Long id) {
        Joke j = em.find(Joke.class, id);
        if (j == null) {
            throw new EntityNotFoundException("Joke no encontrado con id: " + id);
        }
        return j;
    }

    @SuppressWarnings("unchecked")
    public List<Joke> findAll() {
        return em.createQuery("SELECT j FROM Joke j").getResultList();
    }

    @Transactional
    public Joke create(Joke in) {
        // igual que antes...
        // ...
        em.persist(in);
        return in;
    }

    @Transactional
    public Joke update(Long id, Joke datos) {
        // igual que antes...
        return findById(id);
    }

    @Transactional
    public void delete(Long id) {
        Joke j = em.find(Joke.class, id);
        if (j != null) em.remove(j);
    }

    // Métodos auxiliares para poblar selects...
    public List<Category> findAllCategories() {
        TypedQuery<Category> q = em.createQuery("SELECT c FROM Category c", Category.class);
        return q.getResultList();
    }

    public List<Type> findAllTypes() {
        TypedQuery<Type> q = em.createQuery("SELECT t FROM Type t", Type.class);
        return q.getResultList();
    }

    public List<Language> findAllLanguages() {
        TypedQuery<Language> q = em.createQuery("SELECT l FROM Language l", Language.class);
        return q.getResultList();
    }

    public List<Flag> findAllFlags() {
        TypedQuery<Flag> q = em.createQuery("SELECT f FROM Flag f", Flag.class);
        return q.getResultList();
    }

    /** --- Conversión entre entidad <-> DTO plano --- */

    public CreateJokeDTO toCreateDto(Joke j) {
        CreateJokeDTO dto = new CreateJokeDTO();
        dto.setId(j.getId());
        dto.setText1(j.getText1());
        dto.setText2(j.getText2());
        dto.setCategory(j.getCategory() != null ? j.getCategory().getId() : null);
        dto.setType(j.getType() != null ? j.getType().getId() : null);
        dto.setLanguage(j.getLanguage() != null ? j.getLanguage().getId() : null);
        if (j.getFlags() != null) {
            dto.setFlags(
                    j.getFlags().stream()
                            .map(jf -> jf.getFlag().getId())
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }

    @Transactional
    public Joke createFromDto(CreateJokeDTO dto) {
        Joke joke = new Joke();
        joke.setText1(dto.getText1());
        joke.setText2(dto.getText2());

        if (dto.getCategory() != null) {
            Category cat = em.getReference(Category.class, dto.getCategory());
            joke.setCategory(cat);
        }
        if (dto.getType() != null) {
            Type type = em.getReference(Type.class, dto.getType());
            joke.setType(type);
        }
        if (dto.getLanguage() != null) {
            Language lang = em.getReference(Language.class, dto.getLanguage());
            joke.setLanguage(lang);
        }

        if (dto.getFlags() != null) {
            Set<JokeFlag> flags = new HashSet<>();
            for (Long fid : dto.getFlags()) {
                Flag f = em.getReference(Flag.class, fid);
                JokeFlag jf = new JokeFlag();
                jf.setJoke(joke);
                jf.setFlag(f);
                flags.add(jf);
            }
            joke.setFlags(flags);
        }

        em.persist(joke);
        return joke;
    }

    @Transactional
    public Joke updateFromDto(Long id, CreateJokeDTO dto) {
        Joke existente = findById(id);
        existente.setText1(dto.getText1());
        existente.setText2(dto.getText2());

        if (dto.getCategory() != null) {
            Category cat = em.getReference(Category.class, dto.getCategory());
            existente.setCategory(cat);
        } else {
            existente.setCategory(null);
        }

        if (dto.getType() != null) {
            Type type = em.getReference(Type.class, dto.getType());
            existente.setType(type);
        } else {
            existente.setType(null);
        }

        if (dto.getLanguage() != null) {
            Language lang = em.getReference(Language.class, dto.getLanguage());
            existente.setLanguage(lang);
        } else {
            existente.setLanguage(null);
        }

        existente.getFlags().clear();
        if (dto.getFlags() != null) {
            for (Long fid : dto.getFlags()) {
                Flag f = em.getReference(Flag.class, fid);
                JokeFlag jf = new JokeFlag();
                jf.setJoke(existente);
                jf.setFlag(f);
                existente.getFlags().add(jf);
            }
        }

        return existente;
    }
}

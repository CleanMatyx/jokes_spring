package com.matiasborra.jokes.model.services;

import com.matiasborra.jokes.model.dao.*;
import com.matiasborra.jokes.model.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JokeServiceImpl implements IJokeService {

    @Autowired
    private IJokeDAO jokeDAO;
    @Autowired
    private ICategoryDAO categoryDAO;
    @Autowired
    private ITypeDAO typeDAO;
    @Autowired
    private ILanguageDAO languageDAO;
    @Autowired
    private IFlagDAO flagDAO;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Joke> findAll() {
        return (List<Joke>) jokeDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Joke findById(Long id) {
        return jokeDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Joke no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public Joke create(Joke in) {
        Category cat = categoryDAO.getReferenceById(in.getCategory().getId());
        Type type = typeDAO.getReferenceById(in.getType().getId());
        Language lang = languageDAO.getReferenceById(in.getLanguage().getId());

        Joke joke = new Joke();
        joke.setText1(in.getText1());
        joke.setText2(in.getText2());
        joke.setCategory(cat);
        joke.setType(type);
        joke.setLanguage(lang);

        if (in.getFlags() != null) {
            Set<JokeFlag> flags = new HashSet<>();
            for (JokeFlag jf : in.getFlags()) {
                Flag f = flagDAO.getReferenceById(jf.getFlag().getId());
                JokeFlag newJf = new JokeFlag();
                newJf.setJoke(joke);
                newJf.setFlag(f);
                flags.add(newJf);
            }
            joke.setFlags(flags);
        }

        return jokeDAO.save(joke);
    }

    @Override
    @Transactional
    public Joke update(Long id, Joke datos) {
        Joke existing = findById(id);
        existing.setText1(datos.getText1());
        existing.setText2(datos.getText2());

        Category cat = categoryDAO.getReferenceById(datos.getCategory().getId());
        Type type = typeDAO.getReferenceById(datos.getType().getId());
        Language lang = languageDAO.getReferenceById(datos.getLanguage().getId());
        existing.setCategory(cat);
        existing.setType(type);
        existing.setLanguage(lang);

        existing.getFlags().clear();
        if (datos.getFlags() != null) {
            for (JokeFlag jf : datos.getFlags()) {
                Flag f = flagDAO.getReferenceById(jf.getFlag().getId());
                JokeFlag newJf = new JokeFlag();
                newJf.setJoke(existing);
                newJf.setFlag(f);
                existing.getFlags().add(newJf);
            }
        }

        return jokeDAO.save(existing);
    }

    @Override
    public void delete(Long id) {
        jokeDAO.deleteById(id);
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

    public Optional<Flag> findFlagById(Long id) {
        return flagDAO.findById(id);
    }
}

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
        return jokeDAO.findAll();
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
            Set<Flag> flags = new HashSet<>();
            for (Flag inputFlag : in.getFlags()) {
                // Busca el flag por su nombre o atributo único
                Optional<Flag> existingFlag = flagDAO.findByName(inputFlag.getName());
                Flag flag;
                if (existingFlag.isPresent()) {
                    flag = existingFlag.get();
                } else {
                    // Crea y guarda el nuevo flag si no existe
                    flag = new Flag();
                    flag.setName(inputFlag.getName());
                    flag.setFlag(inputFlag.getFlag());
                    flag = flagDAO.save(flag);
                }

                // Agrega el flag al conjunto de flags del chiste
                flags.add(flag);

                // Registra la relación en la tabla jokes_flags
                JokeFlag jokeFlag = new JokeFlag();
                jokeFlag.setJokeId(joke.getId());
                jokeFlag.setFlagId(flag.getId());
                em.persist(jokeFlag);
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
            Set<Flag> flags = new HashSet<>();
            for (Flag inputFlag : datos.getFlags()) {
                // Buscar o crear el flag
                Optional<Flag> existingFlag = flagDAO.findByName(inputFlag.getName());
                Flag flag;
                if (existingFlag.isPresent()) {
                    flag = existingFlag.get();
                } else {
                    flag = new Flag();
                    flag.setName(inputFlag.getName());
                    flag.setFlag(inputFlag.getFlag());
                    flag = flagDAO.save(flag);
                }

                // Agregar el flag al conjunto
                flags.add(flag);

                // Registrar la relación en la tabla jokes_flags
                JokeFlag jokeFlag = new JokeFlag();
                jokeFlag.setJokeId(existing.getId());
                jokeFlag.setFlagId(flag.getId());
                em.persist(jokeFlag);
            }
            existing.setFlags(flags);
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

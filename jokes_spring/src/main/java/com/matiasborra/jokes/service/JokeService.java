package com.matiasborra.jokes.service;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matiasborra.jokes.model.Category;
import com.matiasborra.jokes.model.Flag;
import com.matiasborra.jokes.model.Joke;
import com.matiasborra.jokes.model.JokeFlag;
import com.matiasborra.jokes.model.Language;
import com.matiasborra.jokes.model.Type;
import com.matiasborra.jokes.repository.JokeRepository;

@Service
public class JokeService {

    private final JokeRepository jokeRepo;

    @PersistenceContext
    private EntityManager em;

    public JokeService(JokeRepository jokeRepo) {
        this.jokeRepo = jokeRepo;
    }

    public List<Joke> findAll() {
        return jokeRepo.findAll();
    }

    public Joke findById(Long id) {
        return jokeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Joke no encontrado con id: " + id));
    }

    @Transactional
    public Joke create(Joke in) {
        // Referencias gestionadas sin hit a la base (proxy)
        Category cat  = em.getReference(Category.class, in.getCategory().getId());
        Type     type = em.getReference(Type.class,     in.getType().getId());
        Language lang = em.getReference(Language.class, in.getLanguage().getId());

        // Construye la nueva broma
        Joke joke = new Joke();
        joke.setText1(in.getText1());
        joke.setText2(in.getText2());
        joke.setCategory(cat);
        joke.setType(type);
        joke.setLanguage(lang);

        // Si vienen flags, las enlazamos
        if (in.getFlags() != null) {
            for (JokeFlag incomingJf : in.getFlags()) {
                Flag f = em.getReference(Flag.class, incomingJf.getFlag().getId());
                JokeFlag jf = new JokeFlag();
                jf.setJoke(joke);
                jf.setFlag(f);
                joke.getFlags().add(jf);
            }
        }

        // Persistimos
        return jokeRepo.save(joke);
    }

    @Transactional
    public Joke update(Long id, Joke datos) {
        Joke existente = findById(id);

        existente.setText1(datos.getText1());
        existente.setText2(datos.getText2());

        // Volvemos a enlazar referencias
        Category cat  = em.getReference(Category.class, datos.getCategory().getId());
        Type     type = em.getReference(Type.class,     datos.getType().getId());
        Language lang = em.getReference(Language.class, datos.getLanguage().getId());

        existente.setCategory(cat);
        existente.setType(type);
        existente.setLanguage(lang);

        // Reemplazamos flags si vienen nuevas
        existente.getFlags().clear();
        if (datos.getFlags() != null) {
            for (JokeFlag incomingJf : datos.getFlags()) {
                Flag f = em.getReference(Flag.class, incomingJf.getFlag().getId());
                JokeFlag jf = new JokeFlag();
                jf.setJoke(existente);
                jf.setFlag(f);
                existente.getFlags().add(jf);
            }
        }

        return jokeRepo.save(existente);
    }

    public void delete(Long id) {
        jokeRepo.deleteById(id);
    }
}

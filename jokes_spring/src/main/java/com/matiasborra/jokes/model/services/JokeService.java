package com.matiasborra.jokes.model.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.matiasborra.jokes.model.entity.Category;
import com.matiasborra.jokes.model.entity.Flag;
import com.matiasborra.jokes.model.entity.Joke;
import com.matiasborra.jokes.model.entity.JokeFlag;
import com.matiasborra.jokes.model.entity.Language;
import com.matiasborra.jokes.model.entity.Type;

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
        // 1️⃣ Obtener referencias proxy a las entidades relacionadas
        Category cat    = em.getReference(Category.class, in.getCategory().getId());
        Type     type   = em.getReference(Type.class,     in.getType().getId());
        Language lang   = em.getReference(Language.class, in.getLanguage().getId());

        // 2️⃣ Construir la nueva Joke
        Joke joke = new Joke();
        joke.setText1(in.getText1());
        joke.setText2(in.getText2());
        joke.setCategory(cat);
        joke.setType(type);
        joke.setLanguage(lang);

        // 3️⃣ Si vienen flags, asociarlas
        if (in.getFlags() != null) {
            Set<JokeFlag> jfSet = new HashSet<>();
            for (JokeFlag incomingJf : in.getFlags()) {
                Flag f = em.getReference(Flag.class, incomingJf.getFlag().getId());
                JokeFlag jf = new JokeFlag();
                jf.setJoke(joke);
                jf.setFlag(f);
                jfSet.add(jf);
            }
            joke.setFlags(jfSet);
        }

        // 4️⃣ Persistir
        em.persist(joke);
        // opcional: em.flush();
        return joke;
    }

    @Transactional
    public Joke update(Long id, Joke datos) {
        // 1️⃣ Recuperar la entidad persistida
        Joke existente = findById(id);

        // 2️⃣ Actualizar campos simples
        existente.setText1(datos.getText1());
        existente.setText2(datos.getText2());

        // 3️⃣ Volver a obtener referencias para las relaciones
        Category cat    = em.getReference(Category.class, datos.getCategory().getId());
        Type     type   = em.getReference(Type.class,     datos.getType().getId());
        Language lang   = em.getReference(Language.class, datos.getLanguage().getId());

        existente.setCategory(cat);
        existente.setType(type);
        existente.setLanguage(lang);

        // 4️⃣ Reemplazar flags
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

        // 5️⃣ Merge (aunque por estar en transacción, no siempre es necesario)
        return em.merge(existente);
    }

    @Transactional
    public void delete(Long id) {
        Joke j = em.find(Joke.class, id);
        if (j != null) {
            em.remove(j);
        }
    }
}

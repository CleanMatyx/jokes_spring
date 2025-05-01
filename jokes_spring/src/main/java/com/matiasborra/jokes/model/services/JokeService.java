package com.matiasborra.jokes.model.services;

import com.matiasborra.jokes.dto.CreateJokeDTO;
import com.matiasborra.jokes.dto.CreateJokeDTO.NestedId;
import com.matiasborra.jokes.model.entity.Category;
import com.matiasborra.jokes.model.entity.Flag;
import com.matiasborra.jokes.model.entity.Joke;
import com.matiasborra.jokes.model.entity.JokeFlag;
import com.matiasborra.jokes.model.entity.Language;
import com.matiasborra.jokes.model.entity.Type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
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

    /***** CRUD BÁSICO SOBRE JOKE *****/

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
        // Obtener proxies de las relaciones
        Category cat  = em.getReference(Category.class, in.getCategory().getId());
        Type     type = em.getReference(Type.class,     in.getType().getId());
        Language lang = em.getReference(Language.class, in.getLanguage().getId());

        // Construir la entidad
        Joke joke = new Joke();
        joke.setText1(in.getText1());
        joke.setText2(in.getText2());
        joke.setCategory(cat);
        joke.setType(type);
        joke.setLanguage(lang);

        // Asociar flags si vienen
        if (in.getFlags() != null) {
            Set<JokeFlag> set = new HashSet<>();
            for (JokeFlag incoming : in.getFlags()) {
                Flag f = em.getReference(Flag.class, incoming.getFlag().getId());
                JokeFlag jf = new JokeFlag();
                jf.setJoke(joke);
                jf.setFlag(f);
                set.add(jf);
            }
            joke.setFlags(set);
        }

        em.persist(joke);
        return joke;
    }

    @Transactional
    public Joke update(Long id, Joke datos) {
        Joke existente = findById(id);

        // Actualizar campos básicos
        existente.setText1(datos.getText1());
        existente.setText2(datos.getText2());

        // Actualizar relaciones
        Category cat  = em.getReference(Category.class, datos.getCategory().getId());
        Type     type = em.getReference(Type.class,     datos.getType().getId());
        Language lang = em.getReference(Language.class,  datos.getLanguage().getId());
        existente.setCategory(cat);
        existente.setType(type);
        existente.setLanguage(lang);

        // Reemplazar flags
        existente.getFlags().clear();
        if (datos.getFlags() != null) {
            for (JokeFlag incoming : datos.getFlags()) {
                Flag f = em.getReference(Flag.class, incoming.getFlag().getId());
                JokeFlag jf = new JokeFlag();
                jf.setJoke(existente);
                jf.setFlag(f);
                existente.getFlags().add(jf);
            }
        }

        // Como estamos en transacción, Hibernate sincronizará los cambios
        return existente;
    }

    @Transactional
    public void delete(Long id) {
        Joke j = em.find(Joke.class, id);
        if (j != null) {
            em.remove(j);
        }
    }


    /***** PARA POBLAR <select> EN EL FORMULARIO WEB *****/

    @SuppressWarnings("unchecked")
    public List<Category> findAllCategories() {
        return em.createQuery("SELECT c FROM Category c").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Type> findAllTypes() {
        return em.createQuery("SELECT t FROM Type t").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Language> findAllLanguages() {
        return em.createQuery("SELECT l FROM Language l").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Flag> findAllFlags() {
        return em.createQuery("SELECT f FROM Flag f").getResultList();
    }


    /***** MÉTODOS PARA INTEGRAR CON CreateJokeDTO (objetos anidados) *****/

    /**
     * Convierte una entidad Joke en el DTO con NestedId
     */
    public CreateJokeDTO toCreateDto(Joke j) {
        CreateJokeDTO dto = new CreateJokeDTO();
        dto.setText1(j.getText1());
        dto.setText2(j.getText2());

        NestedId c = new NestedId();
        c.setId(j.getCategory().getId());
        dto.setCategory(c);

        NestedId t = new NestedId();
        t.setId(j.getType().getId());
        dto.setType(t);

        NestedId l = new NestedId();
        l.setId(j.getLanguage().getId());
        dto.setLanguage(l);

        if (j.getFlags() != null) {
            List<NestedId> flags = j.getFlags().stream()
                    .map(jf -> {
                        NestedId n = new NestedId();
                        n.setId(jf.getFlag().getId());
                        return n;
                    })
                    .collect(Collectors.toList());
            dto.setFlags(flags);
        }

        return dto;
    }

    /**
     * Crea una Joke a partir del DTO anidado
     */
    @Transactional
    public Joke createFromDto(CreateJokeDTO dto) {
        Joke in = new Joke();
        in.setText1(dto.getText1());
        in.setText2(dto.getText2());

        in.setCategory(em.getReference(Category.class, dto.getCategory().getId()));
        in.setType(em.getReference(Type.class,     dto.getType().getId()));
        in.setLanguage(em.getReference(Language.class, dto.getLanguage().getId()));

        if (dto.getFlags() != null) {
            Set<JokeFlag> set = new HashSet<>();
            for (NestedId nf : dto.getFlags()) {
                JokeFlag jf = new JokeFlag();
                jf.setJoke(in);
                jf.setFlag(em.getReference(Flag.class, nf.getId()));
                set.add(jf);
            }
            in.setFlags(set);
        }

        return create(in);
    }

    /**
     * Actualiza una Joke a partir del DTO anidado
     */
    @Transactional
    public Joke updateFromDto(Long id, CreateJokeDTO dto) {
        Joke cambios = new Joke();
        cambios.setText1(dto.getText1());
        cambios.setText2(dto.getText2());

        cambios.setCategory(em.getReference(Category.class, dto.getCategory().getId()));
        cambios.setType(em.getReference(Type.class,     dto.getType().getId()));
        cambios.setLanguage(em.getReference(Language.class, dto.getLanguage().getId()));

        if (dto.getFlags() != null) {
            Set<JokeFlag> set = new HashSet<>();
            for (NestedId nf : dto.getFlags()) {
                JokeFlag jf = new JokeFlag();
                jf.setFlag(em.getReference(Flag.class, nf.getId()));
                set.add(jf);
            }
            cambios.setFlags(set);
        }

        return update(id, cambios);
    }

}

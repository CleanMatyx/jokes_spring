package com.matiasborra.jokes.model.services;

import com.matiasborra.jokes.dto.*;
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
import java.util.stream.Collectors;

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
    public List<JokeDto> findAll() {
        return jokeDAO.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public JokeDto findById(Long id) {
        Joke entity = jokeDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Joke no encontrado con id: " + id));
        return toDto(entity);
    }

    @Transactional
    @Override
    public CreateJokeDto create(Joke in) {
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

        Joke saved = jokeDAO.save(joke);
        return toDto(saved);
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
        Joke updated = jokeDAO.save(existing);

        return toDto(updated);
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

    private JokeDto toDto(Joke entity) {
        JokeDto dto = new JokeDto();
        dto.setId(entity.getId());
        dto.setText1(entity.getText1());
        dto.setText2(entity.getText2());

        if (entity.getCategory() != null) {
            CategoryDto c = new CategoryDto();
            c.setId(entity.getCategory().getId());
            c.setCategory(entity.getCategory().getCategory());
            dto.setCategory(c);
        }

        if (entity.getLanguage() != null) {
            LanguageDto l = new LanguageDto();
            l.setId(entity.getLanguage().getId());
            l.setCode(entity.getLanguage().getCode());
            l.setLanguage(entity.getLanguage().getLanguage());
            dto.setLanguage(l);
        }

        if (entity.getFlags() != null) {
            Set<FlagDto> fd = entity.getFlags().stream().map(f -> {
                FlagDto fx = new FlagDto();
                fx.setId(f.getId());
                fx.setName(f.getName());
                fx.setFlag(f.getFlag());
                return fx;
            }).collect(Collectors.toSet());
            dto.setFlags(fd);
        }

        return dto;
    }
}
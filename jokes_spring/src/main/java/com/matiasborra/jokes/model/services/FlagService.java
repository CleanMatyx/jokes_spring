package com.matiasborra.jokes.model.services;

import com.matiasborra.jokes.model.entity.Flag;
import com.matiasborra.jokes.model.dao.FlagDAO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlagService {

    private final FlagDAO repo;

    public FlagService(FlagDAO repo) {
        this.repo = repo;
    }

    public List<Flag> findAll() {
        return repo.findAll();
    }

    public Flag findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flag no encontrada con id: " + id));
    }

    public Flag create(Flag f) {
        return repo.save(f);
    }

    public Flag update(Long id, Flag datos) {
        Flag existing = findById(id);
        existing.setName(datos.getName());
        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

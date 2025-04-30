package com.matiasborra.jokes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.matiasborra.jokes.model.Joke;
import com.matiasborra.jokes.repository.JokeRepository;

@Service
@Transactional
public class JokeService {
    private final JokeRepository repo;
    public JokeService(JokeRepository repo) { this.repo = repo; }

    public List<Joke> findAll() {
        return repo.findAll();
    }

    public Joke findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public Joke create(Joke j) {
        return repo.save(j);
    }

    public Joke update(Long id, Joke j) {
        j.setId(id);
        return repo.save(j);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}

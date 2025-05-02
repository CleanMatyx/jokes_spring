package com.matiasborra.jokes.controller;

import com.matiasborra.jokes.dto.*;
import com.matiasborra.jokes.model.entity.Joke;
import com.matiasborra.jokes.model.services.IJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/jokes")
public class JokeRestController {

    private final IJokeService jokeService;

    @Autowired
    public JokeRestController(IJokeService jokeService) {
        this.jokeService = jokeService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        Map<String, Object> resp = new HashMap<>();
        try {
            List<JokeDto> jokes = jokeService.findAll();
            return new ResponseEntity<>(jokes, HttpStatus.OK);
        } catch (DataAccessException e) {
            resp.put("message", "Error fetching jokes");
            resp.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        try {
            Joke joke = jokeService.findById(id);
            return new ResponseEntity<>(joke, HttpStatus.OK);
        } catch (NoSuchElementException | DataAccessException e) {
            resp.put("message", "Joke not found with id " + id);
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateJokeDto dto, BindingResult result) {
        Map<String, Object> resp = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "Field '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            resp.put("errors", errors);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        try {
            JokeDto created = jokeService.create(dto);
            resp.put("message", "Joke created successfully");
            resp.put("joke", created);
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            resp.put("message", "Error creating joke");
            resp.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody CreateJokeDto dto,
                                    BindingResult result) {
        Map<String, Object> resp = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "Field '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            resp.put("errors", errors);
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        try {
            JokeDto updated = jokeService.update(id, dto);
            resp.put("message", "Joke updated successfully");
            resp.put("joke", updated);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            resp.put("message", "Joke not found with id " + id);
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            resp.put("message", "Error updating joke");
            resp.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();
        try {
            jokeService.delete(id);
            resp.put("message", "Joke deleted successfully");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            resp.put("message", "Joke not found with id " + id);
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            resp.put("message", "Error deleting joke");
            resp.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories() {
        return jokeService.findAllCategories();
    }

    @GetMapping("/types")
    public List<TypeDto> getTypes() {
        return jokeService.findAllTypes();
    }

    @GetMapping("/languages")
    public List<LanguageDto> getLanguages() {
        return jokeService.findAllLanguages();
    }

    @GetMapping("/flags")
    public List<FlagDto> getFlags() {
        return jokeService.findAllFlags();
    }

    @GetMapping("/flags/{id}")
    public ResponseEntity<FlagDto> getFlag(@PathVariable Long id) {
        Optional<FlagDto> flag = jokeService.findFlagById(id);
        return flag.map(f -> new ResponseEntity<>(f, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
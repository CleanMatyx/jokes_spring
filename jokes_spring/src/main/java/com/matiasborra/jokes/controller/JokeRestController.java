package com.matiasborra.jokes.controller;

import com.matiasborra.jokes.model.entity.Joke;
import com.matiasborra.jokes.model.services.IJokeServices;
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
    @Autowired
    private IJokeServices jokeService;

    @GetMapping
    public ResponseEntity<?> index() {
        List<Joke> jokes = new ArrayList<>();
        Map<String, Object> resp = new HashMap<>();

        try {
            jokes = jokeService.findAll();
        } catch (DataAccessException e) {
            resp.put("Message", "Error al realizar la consulta");
            resp.put("Error", e.getMessage()
                    .concat(":")
                    .concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<Joke>>(jokes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Joke joke = null;
        Map<String, Object> resp = new HashMap<>();

        try {
            joke = jokeService.findById(id);
        } catch (DataAccessException e) {
            resp.put("Message", "Error al realizar la consulta");
            resp.put("Error", e.getMessage()
                    .concat(":")
                    .concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (joke == null) {
            resp.put("Message", "El chiste con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Joke>(joke, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Joke joke, BindingResult result) {
        Joke newJoke = null;
        Map<String, Object> resp = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            resp.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            joke.setText1(joke.getText1());
            joke.setText2(joke.getText2());
            joke.setType(joke.getType());
            joke.setCategory(joke.getCategory());
            joke.setLanguage(joke.getLanguage());
            joke.setFlags(joke.getFlags());
            newJoke = jokeService.create(joke);
        } catch (DataAccessException e) {
            resp.put("Message", "Error al realizar el insert");
            resp.put("Error", e.getMessage()
                    .concat(":")
                    .concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("Message", "Chiste creado con éxito");
        resp.put("Chiste", newJoke);
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Joke joke, BindingResult result) {
        Joke jokeToUpdate = null;
        Joke jokeUpdated = null;
        Map<String, Object> resp = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            resp.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            jokeToUpdate = jokeService.findById(id);

            if(jokeToUpdate == null) {
                resp.put("Message", "Error: no se pudo editar, el chiste ID: "
                        .concat(id.toString().concat(" no existe en la base de datos")));
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            resp.put("Message", "Error al realizar la consulta");
            resp.put("Error", e.getMessage()
                    .concat(":")
                    .concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            jokeToUpdate.setText1(joke.getText1());
            jokeToUpdate.setText2(joke.getText2());
            jokeToUpdate.setType(joke.getType());
            jokeToUpdate.setCategory(joke.getCategory());
            jokeToUpdate.setLanguage(joke.getLanguage());
            jokeToUpdate.setFlags(joke.getFlags());
            jokeUpdated = jokeService.update(id, jokeToUpdate);
        } catch (DataAccessException e) {
            resp.put("Message", "Error al realizar la actualización");
            resp.put("Error", e.getMessage()
                    .concat(":")
                    .concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("Message", "Chiste creado correctamente");
        resp.put("Chiste", jokeUpdated);
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();

        try {
            if (jokeService.findById(id) == null) {
                resp.put("Message", "Error: no se pudo eliminar, el chiste ID: "
                        .concat(id.toString().concat(" no existe en la base de datos")));
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }
            jokeService.delete(id);
        } catch (DataAccessException e) {
            resp.put("Message", "Error al realizar la eliminación");
            resp.put("Error", e.getMessage()
                    .concat(":")
                    .concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        resp.put("Message", "Chiste borrado correctamente");
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }
}

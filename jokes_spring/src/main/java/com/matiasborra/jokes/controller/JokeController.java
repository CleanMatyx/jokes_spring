package com.matiasborra.jokes.controller;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.matiasborra.jokes.model.Joke;
import com.matiasborra.jokes.service.JokeService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/jokes")
public class JokeController {

    private final JokeService service;

    public JokeController(JokeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Joke> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joke> one(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

//    @PostMapping
//    public ResponseEntity<Joke> create(@RequestBody Joke j) {
//        Joke creado = service.create(j);
//        return ResponseEntity
//                .created(URI.create("/api/jokes/" + creado.getId()))
//                .body(creado);
//    }

    @PostMapping
    public ResponseEntity<Joke> create(@RequestBody Joke datos) {
        Joke creado = service.create(datos);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())  // <- ya existirá
                .toUri();
        return ResponseEntity.created(location).body(creado);
    }

    @PutMapping("/{id}")
    public Joke update(@PathVariable Long id, @RequestBody Joke j) {
        return service.update(id, j);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

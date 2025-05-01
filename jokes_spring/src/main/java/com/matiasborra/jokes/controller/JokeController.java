package com.matiasborra.jokes.controller;

import com.matiasborra.jokes.dto.CreateJokeDTO;
import com.matiasborra.jokes.dto.FlagDTO;
import com.matiasborra.jokes.dto.JokeResponseDTO;
import com.matiasborra.jokes.dto.CategoryDTO;
import com.matiasborra.jokes.dto.TypeDTO;
import com.matiasborra.jokes.dto.LanguageDTO;
import com.matiasborra.jokes.model.entity.Joke;
import com.matiasborra.jokes.model.entity.Category;
import com.matiasborra.jokes.model.entity.Type;
import com.matiasborra.jokes.model.entity.Language;
import com.matiasborra.jokes.model.entity.Flag;
import com.matiasborra.jokes.model.entity.JokeFlag;
import com.matiasborra.jokes.model.services.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jokes")
public class JokeController {

    @Autowired
    private final JokeService service;

    public JokeController(JokeService service) {
        this.service = service;
    }

    @GetMapping
    public List<JokeResponseDTO> all() {
        return service.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JokeResponseDTO> one(@PathVariable Long id) {
        Joke j = service.findById(id);
        return ResponseEntity.ok(mapToDto(j));
    }

    @PostMapping
    public ResponseEntity<JokeResponseDTO> create(@RequestBody CreateJokeDTO in) {
        Joke toSave = mapToEntity(in);
        Joke saved = service.create(toSave);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(mapToDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JokeResponseDTO> update(@PathVariable Long id, @RequestBody CreateJokeDTO in) {
        Joke toUpdate = mapToEntity(in);
        Joke updated = service.update(id, toUpdate);
        return ResponseEntity.ok(mapToDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private JokeResponseDTO mapToDto(Joke j) {
        JokeResponseDTO dto = new JokeResponseDTO();
        dto.setId(j.getId());
        dto.setText1(j.getText1());
        dto.setText2(j.getText2());

        // Category
        if (j.getCategory() != null) {
            CategoryDTO cd = new CategoryDTO();
            cd.setId(j.getCategory().getId());
            cd.setName(j.getCategory().getName());
            dto.setCategory(cd);
        }

        // Type
        if (j.getType() != null) {
            TypeDTO td = new TypeDTO();
            td.setId(j.getType().getId());
            td.setName(j.getType().getName());
            dto.setType(td);
        }

        // Language
        if (j.getLanguage() != null) {
            LanguageDTO ld = new LanguageDTO();
            ld.setId(j.getLanguage().getId());
            ld.setName(j.getLanguage().getName());
            dto.setLanguage(ld);
        }

        // Flags
        List<FlagDTO> flags = (j.getFlags() == null
                ? Collections.emptyList()
                : j.getFlags().stream()
                .map(jf -> {
                    FlagDTO fd = new FlagDTO();
                    fd.setId(jf.getFlag().getId());
                    fd.setName(jf.getFlag().getName());
                    return fd;
                })
                .collect(Collectors.toList())
        );
        dto.setFlags(flags);

        return dto;

    }

    private Joke mapToEntity(CreateJokeDTO in) {
        Joke j = new Joke();
        j.setText1(in.getText1());
        j.setText2(in.getText2());

        // convierto cada NestedId en su entidad con sólo el id
        Category c = new Category();
        c.setId(in.getCategory());
        j.setCategory(c);

        Type t = new Type();
        t.setId(in.getType());
        j.setType(t);

        Language l = new Language();
        l.setId(in.getLanguage());
        j.setLanguage(l);

        if (in.getFlags() != null) {
            in.getFlags().forEach(nid -> {
                Flag f = new Flag();
                f.setId(nid);

                JokeFlag jf = new JokeFlag();
                jf.setJoke(j);
                jf.setFlag(f);

                j.getFlags().add(jf);
            });
        }

        return j;
    }
}

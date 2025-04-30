package com.matiasborra.jokes.controller;

import com.matiasborra.jokes.dto.FlagDTO;
import com.matiasborra.jokes.model.Flag;
import com.matiasborra.jokes.service.FlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/flags")
public class FlagController {

    private final FlagService service;

    public FlagController(FlagService service) {
        this.service = service;
    }

    @GetMapping
    public List<FlagDTO> all() {
        return service.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlagDTO> one(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(service.findById(id)));
    }

    @PostMapping
    public ResponseEntity<FlagDTO> create(@RequestBody FlagDTO dto) {
        // map DTO → entidad
        Flag toSave = new Flag();
        toSave.setFlag(dto.getName());
        Flag created = service.create(toSave);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(toDto(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlagDTO> update(@PathVariable Long id, @RequestBody FlagDTO dto) {
        Flag toUpdate = new Flag();
        toUpdate.setFlag(dto.getName());
        Flag updated = service.update(id, toUpdate);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private FlagDTO toDto(Flag f) {
        FlagDTO dto = new FlagDTO();
        dto.setId(f.getId());
        dto.setName(f.getFlag());
        return dto;
    }
}

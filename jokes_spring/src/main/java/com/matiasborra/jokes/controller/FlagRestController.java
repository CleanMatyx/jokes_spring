package com.matiasborra.jokes.controller;

import com.matiasborra.jokes.model.entity.Flag;
import com.matiasborra.jokes.model.services.IFlagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/flags")
public class FlagRestController {

    @Autowired
    private IFlagService service;

    @GetMapping
    public ResponseEntity<?> index() {
        List<Flag> flags = new ArrayList<>();
        Map<String, Object> resp = new HashMap<>();

        try {
            flags = service.findAllFlags();
        } catch (Exception e) {
            resp.put("Message", "Error al realizar la consulta");
            resp.put("Error", e.getMessage()
                    .concat(":")
                    .concat(e.getCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<Flag>>(flags, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Flag flag = null;
        Map<String, Object> resp = new HashMap<>();

        try {
            flag = service.findFlagById(id).orElse(null);
        } catch (Exception e) {
            resp.put("Message", "Error al realizar la consulta");
            resp.put("Error", e.getMessage()
                    .concat(":")
                    .concat(e.getCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (flag != null) {
            return new ResponseEntity<Flag>(flag, HttpStatus.OK);
        } else {
            resp.put("Message", "El flag con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Flag flag, BindingResult result) {
        Flag newFlag = null;
        Map<String, Object> resp = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            resp.put("Errors", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            flag.setName(flag.getName());
            newFlag = service.save(flag);
        } catch (DataAccessException e) {
            resp.put("Message", "Error al realizar el insert");
            resp.put("Error", e.getMessage()
                    .concat(":")
                    .concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("Message", "El campo '" + flag.getName() + "' ha sido registrado");
        resp.put("Flag", newFlag);
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Flag flag, BindingResult result) {
        Flag flagToUpdate = null;
        Flag flagUpdated = null;
        Map<String, Object> resp = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            resp.put("Errors", errors);
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
        }

        try {
            flagToUpdate = service.findFlagById(id).orElse(null);
            if (flagToUpdate == null) {
                resp.put("Message", "El flag con ID: "
                        .concat(id.toString().concat(" no existe en la base de datos")));
                return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
            }
            flagToUpdate.setName(flag.getName());
            flagUpdated = service.save(flagToUpdate);
        } catch (DataAccessException e) {
            resp.put("Message", "Error al realizar el update");
            resp.put("Error", e.getMessage()
                    .concat(":")
                    .concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            flagToUpdate.setName(flagUpdated.getName());
            flagUpdated = service.save(flagToUpdate);
        } catch (DataAccessException e) {
            resp.put("Message", "Error al realizar el update");
            resp.put("Error", e.getMessage()
                    .concat(":")
                    .concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        resp.put("Message", "El flag con ID: "
                .concat(flagUpdated.getId().toString().concat(" ha sido actualizado")));
        resp.put("Flag", flagUpdated);
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> resp = new HashMap<>();

        try {
            if (service.findFlagById(id).isPresent()) {
                service.deleteById(id);
            } else {
                resp.put("Message", "El flag con ID: "
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
        resp.put("Message", "El flag con ID: "
                .concat(id.toString().concat(" ha sido eliminado")));
        return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
    }

}

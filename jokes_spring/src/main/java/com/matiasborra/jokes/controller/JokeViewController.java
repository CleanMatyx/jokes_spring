package com.matiasborra.jokes.controller;

import com.matiasborra.jokes.model.entity.Joke;
import com.matiasborra.jokes.model.services.IJokeServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jokes")
public class JokeViewController {

    @Autowired
    private IJokeServices service;

    // 1) Listado de todos los jokes
    @GetMapping
    public String listJokes(Model model) {
        model.addAttribute("jokes", service.findAll());
        model.addAttribute("titulo", "Listado de Jokes");
        return "jokes/list";
    }

    // 2) Formulario para crear uno nuevo
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("joke", new Joke());
        model.addAttribute("titulo", "Nuevo Joke");
        populateFormOptions(model);
        return "jokes/form";
    }

    // 3) Procesar creación
    @PostMapping
    public String createJoke(@Valid @ModelAttribute("joke") Joke joke, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Nuevo Joke");
            populateFormOptions(model);
            return "jokes/form";
        }
        service.create(joke);
        return "redirect:/jokes";
    }

    // 4) Formulario para editar
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Joke existing = service.findById(id);
        model.addAttribute("joke", existing);
        model.addAttribute("titulo", "Editar Joke");
        populateFormOptions(model);
        return "jokes/form";
    }

    // 5) Procesar edición
    @PostMapping("/{id}")
    public String updateJoke(
            @PathVariable Long id,
            @Valid @ModelAttribute("joke") Joke joke,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Editar Joke");
            populateFormOptions(model);
            return "jokes/form";
        }
        service.update(id, joke);
        return "redirect:/jokes";
    }

    // 6) Borrar
    @PostMapping("/{id}/delete")
    public String deleteJoke(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/jokes";
    }

    // Helper para añadir listas de selects en el model
    private void populateFormOptions(Model model) {
        model.addAttribute("categories", service.findAllCategories());
        model.addAttribute("types", service.findAllTypes());
        model.addAttribute("languages", service.findAllLanguages());
        model.addAttribute("flags", service.findAllFlags());
    }
}

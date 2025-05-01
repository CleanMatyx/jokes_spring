package com.matiasborra.jokes.controller;

import com.matiasborra.jokes.dto.CreateJokeDTO;
import com.matiasborra.jokes.model.entity.Category;
import com.matiasborra.jokes.model.entity.Flag;
import com.matiasborra.jokes.model.entity.Joke;
import com.matiasborra.jokes.model.entity.Language;
import com.matiasborra.jokes.model.entity.Type;
import com.matiasborra.jokes.model.services.JokeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jokes")
public class JokeWebController {

    private final JokeService service;

    public JokeWebController(JokeService service) {
        this.service = service;
    }

    // 1) Listado de todos los jokes
    @GetMapping
    public String list(Model model) {
        model.addAttribute("jokes", service.findAll());
        return "jokes/list";
    }

    // 2) Formulario para crear uno nuevo
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("jokeDto", new CreateJokeDTO());
        populateFormOptions(model);
        return "jokes/form";
    }

    // 3) Procesar creación
    @PostMapping
    public String create(@ModelAttribute("jokeDto") CreateJokeDTO dto) {
        service.createFromDto(dto);
        return "redirect:/jokes";
    }

    // 4) Formulario para editar
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        CreateJokeDTO dto = service.toCreateDto(service.findById(id));
        model.addAttribute("jokeDto", dto);
        populateFormOptions(model);
        return "jokes/form";
    }

    // 5) Procesar edición
    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("jokeDto") CreateJokeDTO dto) {
        service.updateFromDto(id, dto);
        return "redirect:/jokes";
    }

    // 6) Borrar
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/jokes";
    }

    // Helper para añadir listas de selects en el model
    private void populateFormOptions(Model model) {
        model.addAttribute("categories", service.findAllCategories());
        model.addAttribute("types",      service.findAllTypes());
        model.addAttribute("languages",  service.findAllLanguages());
        model.addAttribute("flags",      service.findAllFlags());
    }
}

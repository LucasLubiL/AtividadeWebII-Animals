package com.animais.Animais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.animais.Animais.model.Animal;
import com.animais.Animais.service.AnimalService;
//import jakarta.validation.Valid;

@Controller
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @GetMapping("/animal")
    public String index(Model model) {
        model.addAttribute("animalsList", animalService.getAllAnimals());
        return "animal/index";
    }

    @GetMapping("/animal/create")
    public String create(Model model) {
        model.addAttribute("animal", new Animal());
        return "animal/create";
    }

    @PostMapping("/animal/save")
    public String save(@ModelAttribute Animal animal, BindingResult result, Model model) {

        System.out.println(animal);
        if (result.hasErrors()) {
            model.addAttribute("animal", animal);
            return "animal/create";
        }

        animalService.saveAnimal(animal);
        return "redirect:/animal";
    }

    @GetMapping("/animal/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.animalService.deleteAnimalById(id);
        return "redirect:/animal";
    }
    
    @GetMapping("/animal/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Animal animal = animalService.getAnimalById(id);
        model.addAttribute("animal", animal);
        return "animal/edit";
    }
    
}

package com.animais.Animais.impl;

import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.animais.Animais.model.Animal;
import com.animais.Animais.repository.AnimalRepository;
import com.animais.Animais.service.AnimalService;

@Service
public class AnimalServiceImpl implements AnimalService{

    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public void saveAnimal(Animal animal) {
        this.animalRepository.save(animal);
    }

    @Override
    public Animal getAnimalById(long id) {
        Optional<Animal> optional = animalRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Animal not found with id: " + id);
        }
    }

    @Override
    public void deleteAnimalById(long id) {
        this.animalRepository.deleteById(id);
    }

}

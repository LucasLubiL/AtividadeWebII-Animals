package com.animais.Animais.service;
import java.util.List;

import com.animais.Animais.model.Animal;

public interface AnimalService {
    
    List <Animal> getAllAnimals();
    void saveAnimal(Animal animal);
    Animal getAnimalById(long id);
    void deleteAnimalById(long id);

}

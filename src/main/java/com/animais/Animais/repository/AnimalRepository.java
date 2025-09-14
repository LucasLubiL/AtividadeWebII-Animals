package com.animais.Animais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.stereotype.Repository;

import com.animais.Animais.model.Animal;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    
}


package com.animais.Animais.config;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.animais.Animais.service.AnimalService;

@TestConfiguration
public class TestConfig {

    @Bean
    public AnimalService animalService() {
        return Mockito.mock(AnimalService.class);
    }

} 
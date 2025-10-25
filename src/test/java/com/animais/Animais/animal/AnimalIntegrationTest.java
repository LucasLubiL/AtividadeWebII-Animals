package com.animais.Animais.animal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.animais.Animais.model.Animal;
import com.animais.Animais.repository.AnimalRepository;

public class AnimalIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnimalRepository animalRepository;

    @Test
    @WithMockUser(authorities = { "Admin" })
    void testSaveAnimalIntegration() throws Exception {

        Animal animalA = new Animal();
        animalA.setNome("Leoinha");
        animalA.setEspecie("Leoa");
        animalA.setHabitat("Terra");
        animalA.setPeso(20.00);
        animalA.setIdade(15);
        animalA.setEmExtincao(false);
        LocalDate localDate = LocalDate.of(2002, 04, 20);
        Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        animalA.setDataChegada(sqlDate);


        mockMvc.perform(post("/animal/save")
                .with(csrf())
                .flashAttr("animal", animalA))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/animal"));

        // Verifica no banco se foi salvo
        assertTrue(animalRepository.findAll()
                .stream()
                .anyMatch(p -> "Animal A".equals(p.getNome())));

    }

}

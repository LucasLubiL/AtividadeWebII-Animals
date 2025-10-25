package com.animais.Animais.animal;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.animais.Animais.config.TestConfig;
import com.animais.Animais.controller.AnimalController;
import com.animais.Animais.model.Animal;
import com.animais.Animais.service.AnimalService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@WebMvcTest(AnimalController.class)
@Import(TestConfig.class)
public class AnimalControllerTeste {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AnimalService animalService;

    @AfterEach
    void resetMocks() {
        reset(animalService);
    }

    private List<Animal> testCreateAnimalList() {
        Animal animalB = new Animal();
        animalB.setId(1L);
        animalB.setNome("Nome ai");
        animalB.setEspecie("Leão");
        animalB.setHabitat("Terra");
        animalB.setPeso(10.00);
        animalB.setIdade(12);
        animalB.setEmExtincao(false);
        LocalDate localDate = LocalDate.of(2000, 5, 18);
        Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        animalB.setDataChegada(sqlDate);

        return List.of(animalB);
    }

    @Test
    @DisplayName("GET /animal - Listar animais na tela index sem usuário autenticado")
    void testIndexNotAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/animal"))
                .andExpect(status().isUnauthorized()); // Correção aqui
    }

    @Test
    @WithMockUser
    @DisplayName("GET /animal - Listar aniamis na tela index com usuário logado")
    void testIndexAuthenticatedUser() throws Exception {
        when(animalService.getAllAnimals()).thenReturn(testCreateAnimalList());

        mockMvc.perform(get("/animal"))
                .andExpect(status().isOk())
                .andExpect(view().name("animal/index"))
                .andExpect(model().attributeExists("animalsList"))
                .andExpect(content().string(containsString("Listagem de Animal")))
                .andExpect(content().string(containsString("Animal B")));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("GET /animal - Exibe formulário de criação")
    void testCreateFormAuthorizedUser() throws Exception {
        when(animalService.getAllAnimals()).thenReturn(testCreateAnimalList());
        mockMvc.perform(get("/animal"))
                .andExpect(status().isOk())
                .andExpect(view().name("animal/index"))
                .andExpect(content().string(containsString("Cadastrar Animal")));
    }

    @Test
    @WithMockUser(username = "aluno2@iftm.edu.br", authorities = { "Manager" })
    @DisplayName("GET /animal - Verificar o link de cadastrar para um usuario não admin logado")
    void testCreateFormNotAuthorizedUser() throws Exception {
        when(animalService.getAllAnimals()).thenReturn(testCreateAnimalList());
        // Obter o HTML da página renderizada pelo controlador
        mockMvc.perform(get("/animal"))
                .andExpect(status().isOk())
                .andExpect(view().name("animal/index"))
                .andExpect(content().string(
                        not(containsString("<a class=\"dropdown-item\" href=\"/animal/create\">Cadastrar</a>"))));
    }

    @Test
    @WithMockUser
    @DisplayName("POST /animal/save - Falha na validação e retorna para o formulário")
    void testSaveAnimalValidationError() throws Exception {
        Animal product = new Animal(); // Produto sem nome, o que causará erro de validação

        mockMvc.perform(post("/animal/save")
                .with(csrf())
                .flashAttr("animal", product))
                .andExpect(status().isOk())
                .andExpect(view().name("animal/form"))
                .andExpect(model().attributeHasErrors("animal"));

        verify(animalService, never()).saveAnimal(any(Animal.class));
    }

    @Test
    @WithMockUser(username = "aluno@iftm.edu.br", authorities = { "Admin" })
    @DisplayName("POST /animal/save - Produto válido é salvo com sucesso")
    void testSaveValidProduct() throws Exception {
        Animal animal = new Animal();
        animal.setNome("Novo Animal");
        animal.setId(1L);
        animal.setNome("Nome Qualquer");
        animal.setEspecie("Tigre");
        animal.setHabitat("Terra");
        animal.setPeso(20.00);
        animal.setIdade(15);
        animal.setEmExtincao(false);
        LocalDate localDate = LocalDate.of(2002, 04, 20);
        Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        animal.setDataChegada(sqlDate);

        mockMvc.perform(post("/animal/save")
                .with(csrf())
                .flashAttr("animal", animal))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/animal"));

        verify(animalService).saveAnimal(any(Animal.class));
    }

}

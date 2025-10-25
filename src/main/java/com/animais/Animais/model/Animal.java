package com.animais.Animais.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 1, max = 100, message= "Nome deve conter pelo menos 1 caracteres")
    @NotBlank(message= "Nome é um campo obrigatório")
    @NotNull
    @Column(nullable = false, length = 100)
    private String nome;

    @Size(min = 3, max = 50, message= "Espécie deve conter pelo menos 3 caracteres")
    @NotBlank(message= "Espécie é um campo obrigatório")
    @NotNull
    @Column(nullable = false, length = 50)
    private String especie;

    @Size(min = 3, max = 50, message= "Habitat deve conter pelo menos 3 caracteres")
    @NotBlank(message= "Habitat é um campo obrigatório")
    @NotNull
    @Column(length = 50)
    private String habitat;

    @NotNull(message = "Idade é um campo obrigatório")
    private Integer idade;

    @NotNull(message = "Peso é um campo obrigatório")
    @Column(precision = 6)
    private double  peso;

    @NotNull(message = "Em Extinção é um campo obrigatório")
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean emExtincao;

    @NotNull(message = "Data de Chegada é um campo obrigatório")
    private Date dataChegada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public boolean isEmExtincao() {
        return emExtincao;
    }

    public void setEmExtincao(boolean emExtincao) {
        this.emExtincao = emExtincao;
    }

    public Date getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(Date dataChegada) {
        this.dataChegada = dataChegada;
    }
}
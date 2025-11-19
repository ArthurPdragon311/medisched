package com.projeto_final.model.entities;

import java.util.Objects;

/**
 * Classe abstrata que representa uma pessoa no sistema
 * Demonstra: Abstração, Encapsulamento, Herança
 */
public abstract class Pessoa {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    
    // Construtor padrão
    public Pessoa() {
    }
    
    // Construtor com parâmetros
    public Pessoa(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }
    
    public Pessoa(Long id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }
    
    // Método abstrato - deve ser implementado pelas subclasses
    // Demonstra: Abstração e Polimorfismo
    public abstract String getTipo();
    
    // Método abstrato para validação específica
    public abstract boolean validarDados();
    
    // Método concreto que pode ser sobrescrito
    // Demonstra: Polimorfismo (pode ser Override)
    public String getDescricaoCompleta() {
        return getTipo() + ": " + nome + " - " + email;
    }
    
    // Getters e Setters (Encapsulamento)
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
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Métodos auxiliares
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return nome;
    }
}
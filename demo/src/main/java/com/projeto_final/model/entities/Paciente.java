
package com.projeto_final.model.entities;

import java.time.LocalDate;
import java.time.Period;

/**
 * Classe que representa um paciente
 * Demonstra: Herança, Polimorfismo, Encapsulamento, Sobrescrita de métodos
 */
public class Paciente extends Pessoa {
    private String cpf;
    private LocalDate dataNascimento;
    private String endereco;
    
    // Construtores
    public Paciente() {
        super();
    }
    
    public Paciente(String nome, String cpf, LocalDate dataNascimento, 
                   String telefone, String email, String endereco) {
        super(nome, telefone, email); // Chama construtor da superclasse
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }
    
    public Paciente(Long id, String nome, String cpf, LocalDate dataNascimento, 
                   String telefone, String email, String endereco) {
        super(id, nome, telefone, email); // Chama construtor da superclasse
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }
    
    // Implementação dos métodos abstratos (Polimorfismo)
    @Override
    public String getTipo() {
        return "Paciente";
    }
    
    @Override
    public boolean validarDados() {
        // Validação específica de paciente
        if (getNome() == null || getNome().trim().isEmpty()) return false;
        if (cpf == null || cpf.length() != 11) return false;
        if (dataNascimento == null) return false;
        if (getTelefone() == null || getTelefone().trim().isEmpty()) return false;
        return true;
    }
    
    // Sobrescrita de método da superclasse (Polimorfismo)
    @Override
    public String getDescricaoCompleta() {
        return getTipo() + ": " + getNome() + 
               " - CPF: " + cpf + 
               " - Idade: " + calcularIdade() + " anos";
    }
    
    // Getters e Setters (Encapsulamento)
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    // Métodos de negócio específicos
    public int calcularIdade() {
        if (dataNascimento == null) {
            return 0;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
    
    public boolean isMaiorDeIdade() {
        return calcularIdade() >= 18;
    }
    
    @Override
    public String toString() {
        return getNome() + " - CPF: " + cpf;
    }
}
package com.projeto_final.model.entities;

/**
 * Classe que representa um médico
 * Demonstra: Herança, Polimorfismo, Encapsulamento, Sobrescrita de métodos
 */
public class Medico extends Pessoa {
    private String crm;
    private Especialidade especialidade;
    
    // Construtores
    public Medico() {
        super();
    }
    
    public Medico(String nome, String crm, String telefone, 
                 String email, Especialidade especialidade) {
        super(nome, telefone, email); // Chama construtor da superclasse
        this.crm = crm;
        this.especialidade = especialidade;
    }
    
    public Medico(Long id, String nome, String crm, String telefone, 
                 String email, Especialidade especialidade) {
        super(id, nome, telefone, email); // Chama construtor da superclasse
        this.crm = crm;
        this.especialidade = especialidade;
    }
    
    // Implementação dos métodos abstratos (Polimorfismo)
    @Override
    public String getTipo() {
        return "Médico";
    }
    
    @Override
    public boolean validarDados() {
        // Validação específica de médico
        if (getNome() == null || getNome().trim().isEmpty()) return false;
        if (crm == null || crm.trim().isEmpty()) return false;
        if (especialidade == null) return false;
        if (getTelefone() == null || getTelefone().trim().isEmpty()) return false;
        return true;
    }
    
    // Sobrescrita de método da superclasse (Polimorfismo)
    @Override
    public String getDescricaoCompleta() {
        return getTipo() + ": Dr(a). " + getNome() + 
               " - CRM: " + crm + 
               " - " + (especialidade != null ? especialidade.getNome() : "Sem especialidade");
    }
    
    // Getters e Setters (Encapsulamento)
    public String getCrm() {
        return crm;
    }
    
    public void setCrm(String crm) {
        this.crm = crm;
    }
    
    public Especialidade getEspecialidade() {
        return especialidade;
    }
    
    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
    
    // Métodos de negócio específicos
    public boolean podeAtender(Especialidade especialidadeConsulta) {
        return this.especialidade != null && 
               this.especialidade.equals(especialidadeConsulta);
    }
    
    public String getNomeComTitulo() {
        return "Dr(a). " + getNome();
    }
    
    @Override
    public String toString() {
        return getNome() + " - " + (especialidade != null ? especialidade.getNome() : "Sem especialidade");
    }
}
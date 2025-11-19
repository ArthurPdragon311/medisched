package com.projeto_final.model.enums;

/**
 * Enum que representa os possíveis status de uma consulta médica
 */
public enum StatusConsulta {
    AGENDADA("Agendada"),
    REALIZADA("Realizada"),
    CANCELADA("Cancelada");
    
    private final String descricao;
    
    StatusConsulta(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}
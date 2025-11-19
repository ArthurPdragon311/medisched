
package com.projeto_final.model.entities;

import com.projeto_final.model.enums.StatusConsulta;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Classe que representa uma consulta médica
 */
public class Consulta {
    private Long id;
    private LocalDateTime dataHora;
    private StatusConsulta status;
    private String observacoes;
    private Paciente paciente;
    private Medico medico;
    
    // Construtores
    public Consulta() {
        this.status = StatusConsulta.AGENDADA;
    }
    
    public Consulta(LocalDateTime dataHora, Paciente paciente, 
                   Medico medico, String observacoes) {
        this.dataHora = dataHora;
        this.paciente = paciente;
        this.medico = medico;
        this.observacoes = observacoes;
        this.status = StatusConsulta.AGENDADA;
    }
    
    public Consulta(Long id, LocalDateTime dataHora, StatusConsulta status,
                   String observacoes, Paciente paciente, Medico medico) {
        this.id = id;
        this.dataHora = dataHora;
        this.status = status;
        this.observacoes = observacoes;
        this.paciente = paciente;
        this.medico = medico;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    
    public StatusConsulta getStatus() {
        return status;
    }
    
    public void setStatus(StatusConsulta status) {
        this.status = status;
    }
    
    public String getObservacoes() {
        return observacoes;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public Medico getMedico() {
        return medico;
    }
    
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    
    // Métodos de negócio
    public void agendar() {
        this.status = StatusConsulta.AGENDADA;
    }
    
    public void cancelar() {
        this.status = StatusConsulta.CANCELADA;
    }
    
    public void realizar() {
        this.status = StatusConsulta.REALIZADA;
    }
    
    public String getDataHoraFormatada() {
        if (dataHora == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataHora.format(formatter);
    }
    
    // Métodos auxiliares
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(id, consulta.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Consulta #" + id + " - " + 
               (paciente != null ? paciente.getNome() : "Sem paciente") + 
               " com " + 
               (medico != null ? medico.getNome() : "Sem médico") + 
               " - " + getDataHoraFormatada();
    }
}
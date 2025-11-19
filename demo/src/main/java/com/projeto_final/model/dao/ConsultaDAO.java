package com.projeto_final.model.dao;

import com.google.gson.reflect.TypeToken;
import com.projeto_final.model.entities.Consulta;
import com.projeto_final.model.enums.StatusConsulta;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DAO para gerenciar consultas com persistência em JSON
 */
public class ConsultaDAO extends BaseDAO<Consulta> {
    
    public ConsultaDAO() {
        super("consultas"); // Nome do arquivo JSON
    }
    
    @Override
    protected Type getListType() {
        return new TypeToken<List<Consulta>>(){}.getType();
    }
    
    @Override
    protected void setarId(Consulta objeto, Long id) {
        objeto.setId(id);
    }
    
    @Override
    protected Long obterIdDoObjeto(Consulta objeto) {
        return objeto.getId();
    }
    
    @Override
    public void atualizar(Consulta consulta) {
        Consulta existente = buscarPorId(consulta.getId());
        if (existente != null) {
            existente.setDataHora(consulta.getDataHora());
            existente.setStatus(consulta.getStatus());
            existente.setObservacoes(consulta.getObservacoes());
            existente.setPaciente(consulta.getPaciente());
            existente.setMedico(consulta.getMedico());
        }
        super.atualizar(consulta); // Salva no JSON
    }
    
    public List<Consulta> buscarPorPaciente(Long pacienteId) {
        return dados.stream()
                .filter(c -> c.getPaciente() != null && 
                            c.getPaciente().getId().equals(pacienteId))
                .toList();
    }
    
    public List<Consulta> buscarPorMedico(Long medicoId) {
        return dados.stream()
                .filter(c -> c.getMedico() != null && 
                            c.getMedico().getId().equals(medicoId))
                .toList();
    }
    
    public List<Consulta> buscarPorStatus(StatusConsulta status) {
        return dados.stream()
                .filter(c -> c.getStatus() == status)
                .toList();
    }
    
    public boolean verificarDisponibilidade(LocalDateTime dataHora, Long medicoId) {
        return dados.stream()
                .filter(c -> c.getMedico() != null && 
                            c.getMedico().getId().equals(medicoId))
                .filter(c -> c.getStatus() == StatusConsulta.AGENDADA)
                .noneMatch(c -> c.getDataHora().equals(dataHora));
    }
    
    public List<Consulta> listarConsultasFuturas() {
        LocalDateTime agora = LocalDateTime.now();
        return dados.stream()
                .filter(c -> c.getStatus() == StatusConsulta.AGENDADA)
                .filter(c -> c.getDataHora().isAfter(agora))
                .sorted((c1, c2) -> c1.getDataHora().compareTo(c2.getDataHora()))
                .toList();
    }
}
package com.projeto_final.controller;

import com.projeto_final.model.dao.ConsultaDAO;
import com.projeto_final.model.entities.Consulta;
import com.projeto_final.model.enums.StatusConsulta;
import com.projeto_final.util.RepositorioSingleton;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller para gerenciar operações relacionadas a consultas
 * Padrão MVC - Camada Controller
 */
public class ConsultaController {
    
    private final ConsultaDAO consultaDAO;
    
    public ConsultaController() {
        this.consultaDAO = RepositorioSingleton.getInstance().getConsultaDAO();
    }
    
    /**
     * Agenda uma nova consulta
     * @param consulta Consulta a ser agendada
     * @throws IllegalArgumentException se dados inválidos
     */
    public void agendarConsulta(Consulta consulta) {
        validarConsulta(consulta);
        
        // Verifica se a data é futura
        if (consulta.getDataHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar consulta em data passada!");
        }
        
        // Verifica disponibilidade do médico
        if (!verificarDisponibilidade(consulta.getDataHora(), consulta.getMedico().getId())) {
            throw new IllegalArgumentException("Médico já possui consulta agendada neste horário!");
        }
        
        consulta.agendar();
        consultaDAO.inserir(consulta);
    }
    
    /**
     * Atualiza dados de uma consulta
     * @param consulta Consulta com dados atualizados
     * @throws IllegalArgumentException se dados inválidos
     */
    public void atualizarConsulta(Consulta consulta) {
        validarConsulta(consulta);
        
        if (consulta.getId() == null) {
            throw new IllegalArgumentException("ID da consulta não pode ser nulo!");
        }
        
        consultaDAO.atualizar(consulta);
    }
    
    /**
     * Cancela uma consulta
     * @param id ID da consulta
     */
    public void cancelarConsulta(Long id) {
        Consulta consulta = consultaDAO.buscarPorId(id);
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada!");
        }
        
        if (consulta.getStatus() == StatusConsulta.REALIZADA) {
            throw new IllegalArgumentException("Não é possível cancelar uma consulta já realizada!");
        }
        
        consulta.cancelar();
        consultaDAO.atualizar(consulta);
    }
    
    /**
     * Marca uma consulta como realizada
     * @param id ID da consulta
     */
    public void realizarConsulta(Long id) {
        Consulta consulta = consultaDAO.buscarPorId(id);
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não encontrada!");
        }
        
        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new IllegalArgumentException("Não é possível realizar uma consulta cancelada!");
        }
        
        consulta.realizar();
        consultaDAO.atualizar(consulta);
    }
    
    /**
     * Exclui uma consulta
     * @param id ID da consulta
     */
    public void excluirConsulta(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID da consulta não pode ser nulo!");
        }
        consultaDAO.deletar(id);
    }
    
    /**
     * Busca consulta por ID
     * @param id ID da consulta
     * @return Consulta encontrada ou null
     */
    public Consulta buscarConsulta(Long id) {
        return consultaDAO.buscarPorId(id);
    }
    
    /**
     * Lista todas as consultas
     * @return Lista de consultas
     */
    public List<Consulta> listarConsultas() {
        return consultaDAO.listarTodos();
    }
    
    /**
     * Busca consultas de um paciente
     * @param pacienteId ID do paciente
     * @return Lista de consultas do paciente
     */
    public List<Consulta> buscarConsultasPorPaciente(Long pacienteId) {
        return consultaDAO.buscarPorPaciente(pacienteId);
    }
    
    /**
     * Busca consultas de um médico
     * @param medicoId ID do médico
     * @return Lista de consultas do médico
     */
    public List<Consulta> buscarConsultasPorMedico(Long medicoId) {
        return consultaDAO.buscarPorMedico(medicoId);
    }
    
    /**
     * Busca consultas por status
     * @param status Status da consulta
     * @return Lista de consultas com o status especificado
     */
    public List<Consulta> buscarConsultasPorStatus(StatusConsulta status) {
        return consultaDAO.buscarPorStatus(status);
    }
    
    /**
     * Lista consultas futuras agendadas
     * @return Lista de consultas futuras
     */
    public List<Consulta> listarConsultasFuturas() {
        return consultaDAO.listarConsultasFuturas();
    }
    
    /**
     * Verifica se o horário está disponível para o médico
     * @param dataHora Data e hora da consulta
     * @param medicoId ID do médico
     * @return true se disponível
     */
    public boolean verificarDisponibilidade(LocalDateTime dataHora, Long medicoId) {
        return consultaDAO.verificarDisponibilidade(dataHora, medicoId);
    }
    
    /**
     * Valida dados da consulta
     * @param consulta Consulta a ser validada
     * @throws IllegalArgumentException se dados inválidos
     */
    private void validarConsulta(Consulta consulta) {
        if (consulta == null) {
            throw new IllegalArgumentException("Consulta não pode ser nula!");
        }
        
        if (consulta.getDataHora() == null) {
            throw new IllegalArgumentException("Data e hora da consulta são obrigatórios!");
        }
        
        if (consulta.getPaciente() == null) {
            throw new IllegalArgumentException("Paciente é obrigatório!");
        }
        
        if (consulta.getMedico() == null) {
            throw new IllegalArgumentException("Médico é obrigatório!");
        }
    }
}
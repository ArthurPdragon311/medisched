
package com.projeto_final.controller;

import com.projeto_final.model.dao.MedicoDAO;
import com.projeto_final.model.entities.Medico;
import com.projeto_final.util.RepositorioSingleton;
import java.util.List;

/**
 * Controller para gerenciar operações relacionadas a médicos
 * Padrão MVC - Camada Controller
 */
public class MedicoController {
    
    private final MedicoDAO medicoDAO;
    
    public MedicoController() {
        this.medicoDAO = RepositorioSingleton.getInstance().getMedicoDAO();
    }
    
    /**
     * Cadastra um novo médico
     * @param medico Médico a ser cadastrado
     * @throws IllegalArgumentException se dados inválidos
     */
    public void cadastrarMedico(Medico medico) {
        validarMedico(medico);
        
        // Verifica se CRM já existe
        if (medicoDAO.buscarPorCrm(medico.getCrm()) != null) {
            throw new IllegalArgumentException("CRM já cadastrado no sistema!");
        }
        
        medicoDAO.inserir(medico);
    }
    
    /**
     * Atualiza dados de um médico
     * @param medico Médico com dados atualizados
     * @throws IllegalArgumentException se dados inválidos
     */
    public void atualizarMedico(Medico medico) {
        validarMedico(medico);
        
        if (medico.getId() == null) {
            throw new IllegalArgumentException("ID do médico não pode ser nulo!");
        }
        
        // Verifica se CRM já existe em outro médico
        Medico existente = medicoDAO.buscarPorCrm(medico.getCrm());
        if (existente != null && !existente.getId().equals(medico.getId())) {
            throw new IllegalArgumentException("CRM já cadastrado para outro médico!");
        }
        
        medicoDAO.atualizar(medico);
    }
    
    /**
     * Exclui um médico
     * @param id ID do médico
     */
    public void excluirMedico(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do médico não pode ser nulo!");
        }
        medicoDAO.deletar(id);
    }
    
    /**
     * Busca médico por ID
     * @param id ID do médico
     * @return Médico encontrado ou null
     */
    public Medico buscarMedico(Long id) {
        return medicoDAO.buscarPorId(id);
    }
    
    /**
     * Lista todos os médicos
     * @return Lista de médicos
     */
    public List<Medico> listarMedicos() {
        return medicoDAO.listarTodos();
    }
    
    /**
     * Busca médico por CRM
     * @param crm CRM do médico
     * @return Médico encontrado ou null
     */
    public Medico buscarPorCrm(String crm) {
        return medicoDAO.buscarPorCrm(crm);
    }
    
    /**
     * Busca médicos por especialidade
     * @param especialidadeId ID da especialidade
     * @return Lista de médicos da especialidade
     */
    public List<Medico> buscarPorEspecialidade(Long especialidadeId) {
        return medicoDAO.buscarPorEspecialidade(especialidadeId);
    }
    
    /**
     * Busca médicos por nome
     * @param nome Nome ou parte do nome
     * @return Lista de médicos encontrados
     */
    public List<Medico> buscarPorNome(String nome) {
        return medicoDAO.buscarPorNome(nome);
    }
    
    /**
     * Valida dados do médico
     * @param medico Médico a ser validado
     * @throws IllegalArgumentException se dados inválidos
     */
    private void validarMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("Médico não pode ser nulo!");
        }
        
        if (medico.getNome() == null || medico.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do médico é obrigatório!");
        }
        
        if (medico.getCrm() == null || !validarCrm(medico.getCrm())) {
            throw new IllegalArgumentException("CRM inválido!");
        }
        
        if (medico.getEspecialidade() == null) {
            throw new IllegalArgumentException("Especialidade é obrigatória!");
        }
        
        if (medico.getTelefone() == null || medico.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório!");
        }
    }
    
    /**
     * Valida formato do CRM (simplificado)
     * @param crm CRM a ser validado
     * @return true se válido
     */
    public boolean validarCrm(String crm) {
        if (crm == null) return false;
        
        // Remove caracteres não alfanuméricos
        crm = crm.replaceAll("[^0-9A-Z]", "");
        
        // Verifica se tem entre 4 e 10 caracteres
        return crm.length() >= 4 && crm.length() <= 10;
    }
}
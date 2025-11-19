package com.projeto_final.controller;

import com.projeto_final.model.dao.PacienteDAO;
import com.projeto_final.model.entities.Paciente;
import com.projeto_final.util.RepositorioSingleton;
import java.util.List;

/**
 * Controller para gerenciar operações relacionadas a pacientes
 * Padrão MVC - Camada Controller
 */
public class PacienteController {
    
    private final PacienteDAO pacienteDAO;
    
    public PacienteController() {
        this.pacienteDAO = RepositorioSingleton.getInstance().getPacienteDAO();
    }
    
    /**
     * Cadastra um novo paciente
     * @param paciente Paciente a ser cadastrado
     * @throws IllegalArgumentException se dados inválidos
     */
    public void cadastrarPaciente(Paciente paciente) {
        validarPaciente(paciente);
        
        // Verifica se CPF já existe
        if (pacienteDAO.buscarPorCpf(paciente.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema!");
        }
        
        pacienteDAO.inserir(paciente);
    }
    
    /**
     * Atualiza dados de um paciente
     * @param paciente Paciente com dados atualizados
     * @throws IllegalArgumentException se dados inválidos
     */
    public void atualizarPaciente(Paciente paciente) {
        validarPaciente(paciente);
        
        if (paciente.getId() == null) {
            throw new IllegalArgumentException("ID do paciente não pode ser nulo!");
        }
        
        // Verifica se CPF já existe em outro paciente
        Paciente existente = pacienteDAO.buscarPorCpf(paciente.getCpf());
        if (existente != null && !existente.getId().equals(paciente.getId())) {
            throw new IllegalArgumentException("CPF já cadastrado para outro paciente!");
        }
        
        pacienteDAO.atualizar(paciente);
    }
    
    /**
     * Exclui um paciente
     * @param id ID do paciente
     */
    public void excluirPaciente(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do paciente não pode ser nulo!");
        }
        pacienteDAO.deletar(id);
    }
    
    /**
     * Busca paciente por ID
     * @param id ID do paciente
     * @return Paciente encontrado ou null
     */
    public Paciente buscarPaciente(Long id) {
        return pacienteDAO.buscarPorId(id);
    }
    
    /**
     * Lista todos os pacientes
     * @return Lista de pacientes
     */
    public List<Paciente> listarPacientes() {
        return pacienteDAO.listarTodos();
    }
    
    /**
     * Busca paciente por CPF
     * @param cpf CPF do paciente
     * @return Paciente encontrado ou null
     */
    public Paciente buscarPorCpf(String cpf) {
        return pacienteDAO.buscarPorCpf(cpf);
    }
    
    /**
     * Busca pacientes por nome
     * @param nome Nome ou parte do nome
     * @return Lista de pacientes encontrados
     */
    public List<Paciente> buscarPorNome(String nome) {
        return pacienteDAO.buscarPorNome(nome);
    }
    
    /**
     * Valida dados do paciente
     * @param paciente Paciente a ser validado
     * @throws IllegalArgumentException se dados inválidos
     */
    private void validarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não pode ser nulo!");
        }
        
        if (paciente.getNome() == null || paciente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do paciente é obrigatório!");
        }
        
        if (paciente.getCpf() == null || !validarCpf(paciente.getCpf())) {
            throw new IllegalArgumentException("CPF inválido!");
        }
        
        if (paciente.getDataNascimento() == null) {
            throw new IllegalArgumentException("Data de nascimento é obrigatória!");
        }
        
        if (paciente.getTelefone() == null || paciente.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone é obrigatório!");
        }
    }
    
    /**
     * Valida formato do CPF (simplificado)
     * @param cpf CPF a ser validado
     * @return true se válido
     */
    public boolean validarCpf(String cpf) {
        if (cpf == null) return false;
        
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");
        
        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) return false;
        
        // Verifica se todos os dígitos são iguais (CPF inválido)
        if (cpf.matches("(\\d)\\1{10}")) return false;
        
        return true;
    }
}

package com.projeto_final.model.dao;

import com.google.gson.reflect.TypeToken;
import com.projeto_final.model.entities.Paciente;
import java.lang.reflect.Type;
import java.util.List;

/**
 * DAO para gerenciar pacientes com persistência em JSON
 */
public class PacienteDAO extends BaseDAO<Paciente> {
    
    public PacienteDAO() {
        super("pacientes"); // Nome do arquivo JSON
    }
    
    @Override
    protected Type getListType() {
        return new TypeToken<List<Paciente>>(){}.getType();
    }
    
    @Override
    protected void setarId(Paciente objeto, Long id) {
        objeto.setId(id);
    }
    
    @Override
    protected Long obterIdDoObjeto(Paciente objeto) {
        return objeto.getId();
    }
    
    @Override
    public void atualizar(Paciente paciente) {
        Paciente existente = buscarPorId(paciente.getId());
        if (existente != null) {
            existente.setNome(paciente.getNome());
            existente.setCpf(paciente.getCpf());
            existente.setDataNascimento(paciente.getDataNascimento());
            existente.setTelefone(paciente.getTelefone());
            existente.setEmail(paciente.getEmail());
            existente.setEndereco(paciente.getEndereco());
        }
        super.atualizar(paciente); // Salva no JSON
    }
    
    public Paciente buscarPorCpf(String cpf) {
        return dados.stream()
                .filter(p -> p.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
    
    public List<Paciente> buscarPorNome(String nome) {
        return dados.stream()
                .filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList();
    }
}
package com.projeto_final.model.dao;

import com.google.gson.reflect.TypeToken;
import com.projeto_final.model.entities.Medico;
import java.lang.reflect.Type;
import java.util.List;

/**
 * DAO para gerenciar médicos com persistência em JSON
 */
public class MedicoDAO extends BaseDAO<Medico> {
    
    public MedicoDAO() {
        super("medicos"); // Nome do arquivo JSON
    }
    
    @Override
    protected Type getListType() {
        return new TypeToken<List<Medico>>(){}.getType();
    }
    
    @Override
    protected void setarId(Medico objeto, Long id) {
        objeto.setId(id);
    }
    
    @Override
    protected Long obterIdDoObjeto(Medico objeto) {
        return objeto.getId();
    }
    
    @Override
    public void atualizar(Medico medico) {
        Medico existente = buscarPorId(medico.getId());
        if (existente != null) {
            existente.setNome(medico.getNome());
            existente.setCrm(medico.getCrm());
            existente.setTelefone(medico.getTelefone());
            existente.setEmail(medico.getEmail());
            existente.setEspecialidade(medico.getEspecialidade());
        }
        super.atualizar(medico); // Salva no JSON
    }
    
    public Medico buscarPorCrm(String crm) {
        return dados.stream()
                .filter(m -> m.getCrm().equals(crm))
                .findFirst()
                .orElse(null);
    }
    
    public List<Medico> buscarPorEspecialidade(Long especialidadeId) {
        return dados.stream()
                .filter(m -> m.getEspecialidade() != null && 
                            m.getEspecialidade().getId().equals(especialidadeId))
                .toList();
    }
    
    public List<Medico> buscarPorNome(String nome) {
        return dados.stream()
                .filter(m -> m.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList();
    }
}
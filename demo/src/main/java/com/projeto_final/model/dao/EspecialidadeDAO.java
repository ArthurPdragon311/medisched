package com.projeto_final.model.dao;

import com.google.gson.reflect.TypeToken;
import com.projeto_final.model.entities.Especialidade;
import java.lang.reflect.Type;
import java.util.List;

/**
 * DAO para gerenciar especialidades médicas com persistência em JSON
 */
public class EspecialidadeDAO extends BaseDAO<Especialidade> {
    
    public EspecialidadeDAO() {
        super("especialidades"); // Nome do arquivo JSON
        carregarDadosIniciais();
    }
    
    @Override
    protected Type getListType() {
        return new TypeToken<List<Especialidade>>(){}.getType();
    }
    
    @Override
    protected void setarId(Especialidade objeto, Long id) {
        objeto.setId(id);
    }
    
    @Override
    protected Long obterIdDoObjeto(Especialidade objeto) {
        return objeto.getId();
    }
    
    @Override
    public void atualizar(Especialidade especialidade) {
        Especialidade existente = buscarPorId(especialidade.getId());
        if (existente != null) {
            existente.setNome(especialidade.getNome());
            existente.setDescricao(especialidade.getDescricao());
        }
        super.atualizar(especialidade); // Salva no JSON
    }
    
    private void carregarDadosIniciais() {
        // Só carrega se ainda não houver especialidades
        if (dados.isEmpty()) {
            inserir(new Especialidade("Cardiologia", "Especialidade focada em doenças do coração"));
            inserir(new Especialidade("Dermatologia", "Especialidade focada em doenças de pele"));
            inserir(new Especialidade("Ortopedia", "Especialidade focada em ossos e articulações"));
            inserir(new Especialidade("Pediatria", "Especialidade focada em crianças"));
            inserir(new Especialidade("Clínico Geral", "Atendimento médico geral"));
        }
    }
    
}
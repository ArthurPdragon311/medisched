package com.projeto_final.model.dao;

import java.util.List;

/**
 * Interface genérica que define operações CRUD básicas
 * Padrão DAO (Data Access Object)
 * 
 * @param <T> Tipo da entidade
 */
public interface GenericDAO<T> {
    
    /**
     * Insere um novo objeto no repositório
     * @param objeto Objeto a ser inserido
     */
    void inserir(T objeto);
    
    /**
     * Atualiza um objeto existente
     * @param objeto Objeto a ser atualizado
     */
    void atualizar(T objeto);
    
    /**
     * Remove um objeto pelo ID
     * @param id ID do objeto a ser removido
     */
    void deletar(Long id);
    
    /**
     * Busca um objeto pelo ID
     * @param id ID do objeto
     * @return Objeto encontrado ou null
     */
    T buscarPorId(Long id);
    
    /**
     * Lista todos os objetos
     * @return Lista de todos os objetos
     */
    List<T> listarTodos();
}

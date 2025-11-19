package com.projeto_final.util;

import com.projeto_final.model.dao.ConsultaDAO;
import com.projeto_final.model.dao.EspecialidadeDAO;
import com.projeto_final.model.dao.MedicoDAO;
import com.projeto_final.model.dao.PacienteDAO;

/**
 * Singleton que gerencia as instâncias dos DAOs
 * Garante que existe apenas uma instância de cada DAO no sistema
 * Padrão Singleton
 */
public class RepositorioSingleton {
    
    private static RepositorioSingleton instance;
    
    private final PacienteDAO pacienteDAO;
    private final MedicoDAO medicoDAO;
    private final ConsultaDAO consultaDAO;
    private final EspecialidadeDAO especialidadeDAO;
    
    /**
     * Construtor privado para impedir instanciação externa
     */
    private RepositorioSingleton() {
        this.especialidadeDAO = new EspecialidadeDAO();
        this.pacienteDAO = new PacienteDAO();
        this.medicoDAO = new MedicoDAO();
        this.consultaDAO = new ConsultaDAO();
    }
    
    /**
     * Obtém a única instância do repositório (Singleton)
     * @return Instância única do RepositorioSingleton
     */
    public static synchronized RepositorioSingleton getInstance() {
        if (instance == null) {
            instance = new RepositorioSingleton();
        }
        return instance;
    }
    
    // Getters para acessar os DAOs
    
    public PacienteDAO getPacienteDAO() {
        return pacienteDAO;
    }
    
    public MedicoDAO getMedicoDAO() {
        return medicoDAO;
    }
    
    public ConsultaDAO getConsultaDAO() {
        return consultaDAO;
    }
    
    public EspecialidadeDAO getEspecialidadeDAO() {
        return especialidadeDAO;
    }
}
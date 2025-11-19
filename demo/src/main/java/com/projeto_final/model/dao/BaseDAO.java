package com.projeto_final.model.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata base para todos os DAOs com persistência em JSON
 */
public abstract class BaseDAO<T> implements GenericDAO<T> {
    
    protected List<T> dados;
    protected Long proximoId;
    private final String arquivoJson;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();
    
    protected BaseDAO(String nomeArquivo) {
        this.arquivoJson = "dados/" + nomeArquivo + ".json";
        this.dados = new ArrayList<>();
        this.proximoId = 1L;
        criarDiretorioDados();
        carregarDados();
    }
    
    private void criarDiretorioDados() {
        File dir = new File("dados");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    protected abstract Type getListType();
    protected abstract void setarId(T objeto, Long id);
    protected abstract Long obterIdDoObjeto(T objeto);
    
    private void carregarDados() {
        File arquivo = new File(arquivoJson);
        if (arquivo.exists()) {
            try (Reader reader = new FileReader(arquivo)) {
                Type listType = getListType();
                List<T> dadosCarregados = gson.fromJson(reader, listType);
                if (dadosCarregados != null && !dadosCarregados.isEmpty()) {
                    this.dados = dadosCarregados;
                    // Encontra o maior ID para continuar a sequência
                    this.proximoId = dados.stream()
                            .map(this::obterIdDoObjeto)
                            .max(Long::compareTo)
                            .orElse(0L) + 1;
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar dados de " + arquivoJson + ": " + e.getMessage());
            }
        }
    }
    
    private void salvarDados() {
        try (Writer writer = new FileWriter(arquivoJson)) {
            gson.toJson(dados, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados em " + arquivoJson + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void inserir(T objeto) {
        setarId(objeto, proximoId++);
        dados.add(objeto);
        salvarDados();
    }
    
    @Override
    public void deletar(Long id) {
        dados.removeIf(obj -> obterIdDoObjeto(obj).equals(id));
        salvarDados();
    }
    
    @Override
    public T buscarPorId(Long id) {
        return dados.stream()
                .filter(obj -> obterIdDoObjeto(obj).equals(id))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public List<T> listarTodos() {
        return new ArrayList<>(dados);
    }
    
    @Override
    public void atualizar(T objeto) {
        salvarDados();
    }
    
    protected int getTamanho() {
        return dados.size();
    }
    
    protected void limpar() {
        dados.clear();
        proximoId = 1L;
        salvarDados();
    }
}
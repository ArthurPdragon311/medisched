/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto_final.view;

import javax.swing.*;
import java.awt.*;

/**
 * Tela principal do sistema - Menu
 * Padrão MVC - Camada View
 */
public class TelaPrincipal extends JFrame {
    
    public TelaPrincipal() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Sistema de Gerenciamento de Consultas Médicas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Painel superior com título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(41, 128, 185));
        JLabel lblTitulo = new JLabel("Sistema de Consultas Médicas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        // Painel central com botões do menu
        JPanel panelMenu = new JPanel(new GridLayout(4, 1, 10, 10));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        JButton btnPacientes = criarBotaoMenu("Gerenciar Pacientes");
        JButton btnMedicos = criarBotaoMenu("Gerenciar Médicos");
        JButton btnConsultas = criarBotaoMenu("Gerenciar Consultas");
        JButton btnSair = criarBotaoMenu("Sair");
        btnSair.setBackground(new Color(231, 76, 60));
        
        panelMenu.add(btnPacientes);
        panelMenu.add(btnMedicos);
        panelMenu.add(btnConsultas);
        panelMenu.add(btnSair);
        
        add(panelMenu, BorderLayout.CENTER);
        
        // Eventos dos botões
        btnPacientes.addActionListener(e -> abrirTelaPacientes());
        btnMedicos.addActionListener(e -> abrirTelaMedicos());
        btnConsultas.addActionListener(e -> abrirTelaConsultas());
        btnSair.addActionListener(e -> System.exit(0));
    }
    
    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void abrirTelaPacientes() {
        TelaPaciente tela = new TelaPaciente();
        tela.setVisible(true);
    }
    
    private void abrirTelaMedicos() {
        TelaMedico tela = new TelaMedico();
        tela.setVisible(true);
    }
    
    private void abrirTelaConsultas() {
        TelaConsulta tela = new TelaConsulta();
        tela.setVisible(true);
    }
}
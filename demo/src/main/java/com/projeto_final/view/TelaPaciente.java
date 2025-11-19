package com.projeto_final.view;

import com.projeto_final.controller.PacienteController;
import com.projeto_final.model.entities.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaPaciente extends JFrame {
    
    private final PacienteController controller;
    private JTextField txtNome, txtCpf, txtDataNasc, txtTelefone, txtEmail, txtEndereco;
    private JTable tablePacientes;
    private DefaultTableModel tableModel;
    private Paciente pacienteSelecionado;
    
    public TelaPaciente() {
        this.controller = new PacienteController();
        initComponents();
        carregarTabela();
    }
    
    private void initComponents() {
        setTitle("Gerenciar Pacientes");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Painel de formulário
        JPanel panelForm = criarPainelFormulario();
        add(panelForm, BorderLayout.NORTH);
        
        // Painel de tabela
        JPanel panelTabela = criarPainelTabela();
        add(panelTabela, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel panelBotoes = criarPainelBotoes();
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelFormulario() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Paciente"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtDataNasc = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();
        txtEndereco = new JTextField();
        
        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("CPF:"));
        panel.add(txtCpf);
        panel.add(new JLabel("Data Nascimento (dd/MM/yyyy):"));
        panel.add(txtDataNasc);
        panel.add(new JLabel("Telefone:"));
        panel.add(txtTelefone);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Endereço:"));
        panel.add(txtEndereco);
        
        return panel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Pacientes Cadastrados"));
        
        String[] colunas = {"ID", "Nome", "CPF", "Data Nasc.", "Telefone", "Email"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablePacientes = new JTable(tableModel);
        tablePacientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                carregarPacienteSelecionado();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablePacientes);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel criarPainelBotoes() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnNovo = new JButton("Novo");
        JButton btnSalvar = new JButton("Salvar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");
        
        btnNovo.addActionListener(e -> limparCampos());
        btnSalvar.addActionListener(e -> salvarPaciente());
        btnAtualizar.addActionListener(e -> atualizarPaciente());
        btnExcluir.addActionListener(e -> excluirPaciente());
        btnLimpar.addActionListener(e -> limparCampos());
        
        panel.add(btnNovo);
        panel.add(btnSalvar);
        panel.add(btnAtualizar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);
        
        return panel;
    }
    
    private void salvarPaciente() {
        try {
            Paciente paciente = new Paciente();
            paciente.setNome(txtNome.getText());
            paciente.setCpf(txtCpf.getText());
            paciente.setDataNascimento(parseData(txtDataNasc.getText()));
            paciente.setTelefone(txtTelefone.getText());
            paciente.setEmail(txtEmail.getText());
            paciente.setEndereco(txtEndereco.getText());
            
            controller.cadastrarPaciente(paciente);
            JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarPaciente() {
        if (pacienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela!");
            return;
        }
        
        try {
            pacienteSelecionado.setNome(txtNome.getText());
            pacienteSelecionado.setCpf(txtCpf.getText());
            pacienteSelecionado.setDataNascimento(parseData(txtDataNasc.getText()));
            pacienteSelecionado.setTelefone(txtTelefone.getText());
            pacienteSelecionado.setEmail(txtEmail.getText());
            pacienteSelecionado.setEndereco(txtEndereco.getText());
            
            controller.atualizarPaciente(pacienteSelecionado);
            JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirPaciente() {
        if (pacienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um paciente na tabela!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente excluir o paciente " + pacienteSelecionado.getNome() + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            controller.excluirPaciente(pacienteSelecionado.getId());
            JOptionPane.showMessageDialog(this, "Paciente excluído com sucesso!");
            limparCampos();
            carregarTabela();
        }
    }
    
    private void carregarPacienteSelecionado() {
        int row = tablePacientes.getSelectedRow();
        if (row >= 0) {
            Long id = (Long) tableModel.getValueAt(row, 0);
            pacienteSelecionado = controller.buscarPaciente(id);
            
            if (pacienteSelecionado != null) {
                txtNome.setText(pacienteSelecionado.getNome());
                txtCpf.setText(pacienteSelecionado.getCpf());
                txtDataNasc.setText(formatarData(pacienteSelecionado.getDataNascimento()));
                txtTelefone.setText(pacienteSelecionado.getTelefone());
                txtEmail.setText(pacienteSelecionado.getEmail());
                txtEndereco.setText(pacienteSelecionado.getEndereco());
            }
        }
    }
    
    private void carregarTabela() {
        tableModel.setRowCount(0);
        for (Paciente p : controller.listarPacientes()) {
            Object[] row = {
                p.getId(),
                p.getNome(),
                p.getCpf(),
                formatarData(p.getDataNascimento()),
                p.getTelefone(),
                p.getEmail()
            };
            tableModel.addRow(row);
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtDataNasc.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        pacienteSelecionado = null;
        tablePacientes.clearSelection();
    }
    
    private LocalDate parseData(String data) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data inválida! Use o formato dd/MM/yyyy");
        }
    }
    
    private String formatarData(LocalDate data) {
        if (data == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }
}
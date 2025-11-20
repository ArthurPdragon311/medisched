package com.projeto_final.view;

import com.projeto_final.controller.MedicoController;
import com.projeto_final.model.dao.EspecialidadeDAO;
import com.projeto_final.model.entities.Especialidade;
import com.projeto_final.model.entities.Medico;
import com.projeto_final.util.RepositorioSingleton;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Tela de gerenciamento de médicos
 * Padrão MVC - Camada View
 */
public class TelaMedico extends JFrame {
    
    private final MedicoController controller;
    private final EspecialidadeDAO especialidadeDAO;
    private JTextField txtNome, txtCrm, txtTelefone, txtEmail;
    private JComboBox<Especialidade> comboEspecialidade;
    private JTable tableMedicos;
    private DefaultTableModel tableModel;
    private Medico medicoSelecionado;
    
    public TelaMedico() {
        this.controller = new MedicoController();
        this.especialidadeDAO = RepositorioSingleton.getInstance().getEspecialidadeDAO();
        initComponents();
        carregarTabela();
    }
    
    private void initComponents() {
        setTitle("Gerenciar Médicos");
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
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Dados do Medico"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        txtNome = new JTextField();
        txtCrm = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();
        comboEspecialidade = new JComboBox<>();
        
        // Carregar especialidades
        for (Especialidade esp : especialidadeDAO.listarTodos()) {
            comboEspecialidade.addItem(esp);
        }
        
        panel.add(new JLabel("Nome:"));
        panel.add(txtNome);
        panel.add(new JLabel("CRM:"));
        panel.add(txtCrm);
        panel.add(new JLabel("Especialidade:"));
        panel.add(comboEspecialidade);
        panel.add(new JLabel("Telefone:"));
        panel.add(txtTelefone);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        
        return panel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Médicos Cadastrados"));
        
        String[] colunas = {"ID", "Nome", "CRM", "Especialidade", "Telefone", "Email"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableMedicos = new JTable(tableModel);
        tableMedicos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                carregarMedicoSelecionado();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tableMedicos);
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
        btnSalvar.addActionListener(e -> salvarMedico());
        btnAtualizar.addActionListener(e -> atualizarMedico());
        btnExcluir.addActionListener(e -> excluirMedico());
        btnLimpar.addActionListener(e -> limparCampos());
        
        panel.add(btnNovo);
        panel.add(btnSalvar);
        panel.add(btnAtualizar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);
        
        return panel;
    }
    
    private void salvarMedico() {
        try {
            Medico medico = new Medico();
            medico.setNome(txtNome.getText());
            medico.setCrm(txtCrm.getText());
            medico.setTelefone(txtTelefone.getText());
            medico.setEmail(txtEmail.getText());
            medico.setEspecialidade((Especialidade) comboEspecialidade.getSelectedItem());
            
            controller.cadastrarMedico(medico);
            JOptionPane.showMessageDialog(this, "Médico cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void atualizarMedico() {
        if (medicoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um médico na tabela!");
            return;
        }
        
        try {
            medicoSelecionado.setNome(txtNome.getText());
            medicoSelecionado.setCrm(txtCrm.getText());
            medicoSelecionado.setTelefone(txtTelefone.getText());
            medicoSelecionado.setEmail(txtEmail.getText());
            medicoSelecionado.setEspecialidade((Especialidade) comboEspecialidade.getSelectedItem());
            
            controller.atualizarMedico(medicoSelecionado);
            JOptionPane.showMessageDialog(this, "Médico atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirMedico() {
        if (medicoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um médico na tabela!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente excluir o médico " + medicoSelecionado.getNome() + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            controller.excluirMedico(medicoSelecionado.getId());
            JOptionPane.showMessageDialog(this, "Médico excluído com sucesso!");
            limparCampos();
            carregarTabela();
        }
    }
    
    private void carregarMedicoSelecionado() {
        int row = tableMedicos.getSelectedRow();
        if (row >= 0) {
            Long id = (Long) tableModel.getValueAt(row, 0);
            medicoSelecionado = controller.buscarMedico(id);
            
            if (medicoSelecionado != null) {
                txtNome.setText(medicoSelecionado.getNome());
                txtCrm.setText(medicoSelecionado.getCrm());
                txtTelefone.setText(medicoSelecionado.getTelefone());
                txtEmail.setText(medicoSelecionado.getEmail());
                comboEspecialidade.setSelectedItem(medicoSelecionado.getEspecialidade());
            }
        }
    }
    
    private void carregarTabela() {
        tableModel.setRowCount(0);
        for (Medico m : controller.listarMedicos()) {
            Object[] row = {
                m.getId(),
                m.getNome(),
                m.getCrm(),
                m.getEspecialidade() != null ? m.getEspecialidade().getNome() : "",
                m.getTelefone(),
                m.getEmail()
            };
            tableModel.addRow(row);
        }
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtCrm.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        comboEspecialidade.setSelectedIndex(0);
        medicoSelecionado = null;
        tableMedicos.clearSelection();
    }
}
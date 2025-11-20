package com.projeto_final.view;

import com.projeto_final.controller.ConsultaController;
import com.projeto_final.controller.MedicoController;
import com.projeto_final.controller.PacienteController;
import com.projeto_final.model.entities.Consulta;
import com.projeto_final.model.entities.Medico;
import com.projeto_final.model.entities.Paciente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Tela de gerenciamento de consultas
 * Padrão MVC - Camada View
 */
public class TelaConsulta extends JFrame {
    
    private final ConsultaController consultaController;
    private final PacienteController pacienteController;
    private final MedicoController medicoController;
    
    private JComboBox<Paciente> comboPaciente;
    private JComboBox<Medico> comboMedico;
    private JTextField txtDataHora, txtObservacoes;
    private JTable tableConsultas;
    private DefaultTableModel tableModel;
    private Consulta consultaSelecionada;
    
    public TelaConsulta() {
        this.consultaController = new ConsultaController();
        this.pacienteController = new PacienteController();
        this.medicoController = new MedicoController();
        initComponents();
        carregarTabela();
    }
    
    private void initComponents() {
        setTitle("Gerenciar Consultas");
        setSize(1000, 600);
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
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Dados da Consulta"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        comboPaciente = new JComboBox<>();
        comboMedico = new JComboBox<>();
        txtDataHora = new JTextField();
        txtObservacoes = new JTextField();
        
        // Carregar pacientes e médicos
        for (Paciente p : pacienteController.listarPacientes()) {
            comboPaciente.addItem(p);
        }
        
        for (Medico m : medicoController.listarMedicos()) {
            comboMedico.addItem(m);
        }
        
        panel.add(new JLabel("Paciente:"));
        panel.add(comboPaciente);
        panel.add(new JLabel("Médico:"));
        panel.add(comboMedico);
        panel.add(new JLabel("Data/Hora (dd/MM/yyyy HH:mm):"));
        panel.add(txtDataHora);
        panel.add(new JLabel("Observações:"));
        panel.add(txtObservacoes);
        
        return panel;
    }
    
    private JPanel criarPainelTabela() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Consultas Agendadas"));
        
        String[] colunas = {"ID", "Paciente", "Médico", "Data/Hora", "Status", "Observações"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableConsultas = new JTable(tableModel);
        tableConsultas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                carregarConsultaSelecionada();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tableConsultas);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel criarPainelBotoes() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnNovo = new JButton("Novo");
        JButton btnAgendar = new JButton("Agendar");
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnRealizar = new JButton("Realizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");
        
        btnNovo.addActionListener(e -> limparCampos());
        btnAgendar.addActionListener(e -> agendarConsulta());
        btnCancelar.addActionListener(e -> cancelarConsulta());
        btnRealizar.addActionListener(e -> realizarConsulta());
        btnExcluir.addActionListener(e -> excluirConsulta());
        btnLimpar.addActionListener(e -> limparCampos());
        
        panel.add(btnNovo);
        panel.add(btnAgendar);
        panel.add(btnCancelar);
        panel.add(btnRealizar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);
        
        return panel;
    }
    
    private void agendarConsulta() {
        try {
            Paciente paciente = (Paciente) comboPaciente.getSelectedItem();
            Medico medico = (Medico) comboMedico.getSelectedItem();
            
            if (paciente == null || medico == null) {
                JOptionPane.showMessageDialog(this, "Selecione um paciente e um médico!");
                return;
            }
            
            LocalDateTime dataHora = parseDataHora(txtDataHora.getText());
            
            Consulta consulta = new Consulta();
            consulta.setPaciente(paciente);
            consulta.setMedico(medico);
            consulta.setDataHora(dataHora);
            consulta.setObservacoes(txtObservacoes.getText());
            
            consultaController.agendarConsulta(consulta);
            JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cancelarConsulta() {
        if (consultaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta na tabela!");
            return;
        }
        
        try {
            consultaController.cancelarConsulta(consultaSelecionada.getId());
            JOptionPane.showMessageDialog(this, "Consulta cancelada com sucesso!");
            limparCampos();
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void realizarConsulta() {
        if (consultaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta na tabela!");
            return;
        }
        
        try {
            consultaController.realizarConsulta(consultaSelecionada.getId());
            JOptionPane.showMessageDialog(this, "Consulta marcada como realizada!");
            limparCampos();
            carregarTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void excluirConsulta() {
        if (consultaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta na tabela!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Deseja realmente excluir esta consulta?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            consultaController.excluirConsulta(consultaSelecionada.getId());
            JOptionPane.showMessageDialog(this, "Consulta excluída com sucesso!");
            limparCampos();
            carregarTabela();
        }
    }
    
    private void carregarConsultaSelecionada() {
        int row = tableConsultas.getSelectedRow();
        if (row >= 0) {
            Long id = (Long) tableModel.getValueAt(row, 0);
            consultaSelecionada = consultaController.buscarConsulta(id);
            
            if (consultaSelecionada != null) {
                comboPaciente.setSelectedItem(consultaSelecionada.getPaciente());
                comboMedico.setSelectedItem(consultaSelecionada.getMedico());
                txtDataHora.setText(formatarDataHora(consultaSelecionada.getDataHora()));
                txtObservacoes.setText(consultaSelecionada.getObservacoes());
            }
        }
    }
    
    private void carregarTabela() {
        tableModel.setRowCount(0);
        for (Consulta c : consultaController.listarConsultas()) {
            Object[] row = {
                c.getId(),
                c.getPaciente() != null ? c.getPaciente().getNome() : "",
                c.getMedico() != null ? c.getMedico().getNome() : "",
                c.getDataHoraFormatada(),
                c.getStatus().getDescricao(),
                c.getObservacoes()
            };
            tableModel.addRow(row);
        }
    }
    
    private void limparCampos() {
        if (comboPaciente.getItemCount() > 0) {
            comboPaciente.setSelectedIndex(0);
        }
        if (comboMedico.getItemCount() > 0) {
            comboMedico.setSelectedIndex(0);
        }
        txtDataHora.setText("");
        txtObservacoes.setText("");
        consultaSelecionada = null;
        tableConsultas.clearSelection();
    }
    
    private LocalDateTime parseDataHora(String dataHora) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return LocalDateTime.parse(dataHora, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data/Hora inválida! Use o formato dd/MM/yyyy HH:mm");
        }
    }
    
    private String formatarDataHora(LocalDateTime dataHora) {
        if (dataHora == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataHora.format(formatter);
    }
}
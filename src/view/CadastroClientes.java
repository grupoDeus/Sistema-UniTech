package view;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CadastroClientes extends javax.swing.JFrame {

    String idClientes = "C:\\Arquivos Unitech\\sequenciaClientes.txt";
    int codigo;
    int linha;

    String dadosClientes = "C:\\Arquivos Unitech\\dadosClientes.dat";
    Object[][] clientes;

    void recuperaCodigoSequencia() {

        codigo = 1;
        try {
            File f = new File(idClientes);
            Scanner s = new Scanner(f);
            if (s.hasNext()) {
                String nS = s.nextLine();
                codigo = Integer.parseInt(nS) + 1;
            }
            s.close();
        } catch (Exception ex) {

        }
    }

    void gravarCodigoSequencia() {
        try {
            File f = new File(idClientes);
            PrintStream ps = new PrintStream(f);
            ps.print("" + codigo);
            ps.close();
        } catch (Exception ex) {

        }
    }

    void alteraCoresTabela() {
        Color corLinha = new Color(51, 51, 51);

        tbCliente.setRowHeight(20);
        tbCliente.setRowHeight(1, 100);

        tbCliente.setShowGrid(true);
        tbCliente.setGridColor(corLinha);

        tbCliente.setBackground(corLinha);
        tbCliente.setForeground(Color.WHITE);

        tbCliente.setSelectionBackground(corLinha);
        tbCliente.setSelectionForeground(Color.BLACK);

        tbCliente.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));

    }

    void insereTabela(String nome, String telefone, String cpf, int idade, int codigo) {
        DefaultTableModel dtm = (DefaultTableModel) tbCliente.getModel();
        dtm.addRow(new Object[]{nome, telefone, cpf, idade, codigo});
    }

    void transformaTabelaMatriz() {
        DefaultTableModel dtm = (DefaultTableModel) tbCliente.getModel();

        int colunas = dtm.getColumnCount();
        int linhas = dtm.getRowCount();
        clientes = new Object[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            clientes[i][0] = dtm.getValueAt(i, 0);
            clientes[i][1] = dtm.getValueAt(i, 1);
            clientes[i][2] = dtm.getValueAt(i, 2);
            clientes[i][3] = dtm.getValueAt(i, 3);
            clientes[i][4] = dtm.getValueAt(i, 4);
        }

    }

    public void mudaCabecalhoTabela() {
        Color corCabecalho = new Color(10, 255, 108);
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(corCabecalho);

        for (int i = 0; i < tbCliente.getModel().getColumnCount(); i++) {
            tbCliente.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }

    void recuperaDadosArquivo() {
        try {
            File f = new File(dadosClientes);
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            clientes = (Object[][]) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception ex) {

        }
    }

    private void mudaIconeJframe() {
        this.setIconImage(new ImageIcon(getClass().getResource("/imagens/unitech52px.png")).getImage());

    }

    void transformaMatrizTabela() {
        DefaultTableModel dtm = (DefaultTableModel) tbCliente.getModel();
        dtm.setRowCount(0);

        for (int i = 0; i < clientes.length; i++) {
            String nome = (String) clientes[i][0];
            String telefone = (String) clientes[i][1];
            String cpf = (String) clientes[i][2];
            int idade = (int) clientes[i][3];
            int codigo = (int) clientes[i][4];
            insereTabela(nome, telefone, cpf, idade, codigo);
        }
    }

    void gravaDadosArquivo() {
        try {
            File f = new File(dadosClientes);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(clientes);
            oos.close();
            fos.close();
        } catch (Exception ex) {

        }

    }

    void excluiRegistroTabela() {
        DefaultTableModel dtm = (DefaultTableModel) tbCliente.getModel();
        if (tbCliente.getSelectedRow() >= 0) {
            dtm.removeRow(tbCliente.getSelectedRow());
            tbCliente.setModel(dtm);
        } else {
            JOptionPane.showMessageDialog(null, "Favor selecionar uma linha");
        }
    }

    void pegaLinhaSelecionada() {
        linha = tbCliente.getSelectedRow();
        
        

        JOptionPane.showMessageDialog(null, " Linha selecionada: " + (linha + 1));

    }

    void excluiRegistroArquivo() throws IOException {
        String contatoAExcluir = "";

        FileReader fileReader = null;
        FileWriter fileWriter = null;
        BufferedReader leitor = null;

        String nomeDoArquivo = "C:\\Arquivos Unitech\\dadosClientes.dat";
        String arquivoConferir = "C:\\Arquivos Unitech\\dadosClientes_new.txt";
        String line = "";

        try {
            fileReader = new FileReader(new File(nomeDoArquivo));
            fileWriter = new FileWriter(new File(arquivoConferir));
            leitor = new BufferedReader(fileReader);
            line = "";
            while ((line = leitor.readLine()) != null) {
                if (!line.trim().equals(contatoAExcluir.trim())) {
                    fileWriter.write(line + "\r\n");
                }
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileWriter.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       
    }
     void pegaValorExcluido(){
         int valorExcluido=tbCliente.getRowCount();
         if (valorExcluido<=0) {
             JOptionPane.showMessageDialog(null, "A Linha excluida foi á : " + (valorExcluido));
             
             
         }
        
    }

    public CadastroClientes() {
        initComponents();
        //icone jframe
        mudaIconeJframe();

//inicia tela no centro
        setLocationRelativeTo(null);
//muda cor do jframe
        Color minhaCor = new Color(51, 51, 51);
        getContentPane().setBackground(minhaCor);
        alteraCoresTabela();
        mudaCabecalhoTabela();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCpf = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtIdade = new javax.swing.JTextField();
        bt_Excluir = new javax.swing.JButton();
        bt_Criar = new javax.swing.JButton();
        bt_Salvar = new javax.swing.JButton();
        bt_Alterar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbCliente = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(10, 255, 108));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/unitech.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel1.setBackground(new java.awt.Color(10, 255, 108));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(10, 255, 108));
        jLabel1.setText("CADASTRO DE CLIENTES");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(170, 170, 170)
                .addComponent(jLabel1)
                .addContainerGap(304, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("Nome");

        txtNome.setBackground(new java.awt.Color(255, 255, 255));
        txtNome.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNome.setForeground(new java.awt.Color(0, 0, 0));

        txtTelefone.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefone.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("Telefone");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("CPF");

        txtCpf.setBackground(new java.awt.Color(255, 255, 255));
        txtCpf.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Idade");

        txtIdade.setBackground(new java.awt.Color(255, 255, 255));
        txtIdade.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdade, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 185, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36))
        );

        bt_Excluir.setBackground(new java.awt.Color(10, 255, 108));
        bt_Excluir.setFont(new java.awt.Font("SansSerif", 3, 14)); // NOI18N
        bt_Excluir.setForeground(new java.awt.Color(0, 0, 0));
        bt_Excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/excluir.png"))); // NOI18N
        bt_Excluir.setText("Excluir");
        bt_Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ExcluirActionPerformed(evt);
            }
        });

        bt_Criar.setBackground(new java.awt.Color(10, 255, 108));
        bt_Criar.setFont(new java.awt.Font("SansSerif", 3, 14)); // NOI18N
        bt_Criar.setForeground(new java.awt.Color(0, 0, 0));
        bt_Criar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/criar.png"))); // NOI18N
        bt_Criar.setText("Criar");
        bt_Criar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_CriarMouseClicked(evt);
            }
        });
        bt_Criar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_CriarActionPerformed(evt);
            }
        });

        bt_Salvar.setBackground(new java.awt.Color(10, 255, 108));
        bt_Salvar.setFont(new java.awt.Font("SansSerif", 3, 14)); // NOI18N
        bt_Salvar.setForeground(new java.awt.Color(0, 0, 0));
        bt_Salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/save.png"))); // NOI18N
        bt_Salvar.setText("Salvar");
        bt_Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_SalvarActionPerformed(evt);
            }
        });

        bt_Alterar.setBackground(new java.awt.Color(10, 255, 108));
        bt_Alterar.setFont(new java.awt.Font("SansSerif", 3, 14)); // NOI18N
        bt_Alterar.setForeground(new java.awt.Color(0, 0, 0));
        bt_Alterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/alterar.png"))); // NOI18N
        bt_Alterar.setText("Alterar");
        bt_Alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_AlterarActionPerformed(evt);
            }
        });

        tbCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tbCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Telefone", "CPF", "Idade", "Código Cliente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbCliente);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(bt_Criar, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(bt_Alterar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(bt_Salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bt_Excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_Criar)
                    .addComponent(bt_Alterar)
                    .addComponent(bt_Salvar)
                    .addComponent(bt_Excluir))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bt_Alterar, bt_Criar, bt_Excluir, bt_Salvar});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ExcluirActionPerformed

        int resposta = JOptionPane.showConfirmDialog(null, "Você quer realmente cancelar este registro?",
                "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            excluiRegistroTabela();
            pegaValorExcluido();
            JOptionPane.showMessageDialog(null, "Exclusão feita com sucesso",
                    "Exclusão Cliente",JOptionPane.INFORMATION_MESSAGE);
            try {
                excluiRegistroArquivo();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else if (resposta == JOptionPane.NO_OPTION) {

        }


    }//GEN-LAST:event_bt_ExcluirActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        recuperaDadosArquivo();
        transformaMatrizTabela();

    }//GEN-LAST:event_formWindowOpened

    private void bt_CriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_CriarActionPerformed
        txtNome.setText("");
        txtTelefone.setText("");
        txtCpf.setText("");
        txtIdade.setText("");

    }//GEN-LAST:event_bt_CriarActionPerformed

    private void bt_SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_SalvarActionPerformed
        recuperaCodigoSequencia();
        int novoCodigo = codigo;
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String cpf = txtCpf.getText();
        int idade = Integer.parseInt(txtIdade.getText());
        insereTabela(nome, telefone, cpf, idade, novoCodigo);
        transformaTabelaMatriz();
        gravaDadosArquivo();
        gravarCodigoSequencia();

        JOptionPane.showMessageDialog(null, "Dados salvos com sucesso");


    }//GEN-LAST:event_bt_SalvarActionPerformed

    private void bt_CriarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_CriarMouseClicked
        txtNome.setText("");
        txtTelefone.setText("");
        txtCpf.setText("");
        txtIdade.setText(" ");

    }//GEN-LAST:event_bt_CriarMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated

    private void bt_AlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_AlterarActionPerformed
        int linhaSelecionada = tbCliente.getSelectedRow();
        if (linhaSelecionada >= 0) {
            DefaultTableModel dtm = (DefaultTableModel) tbCliente.getModel();
            String nome = txtNome.getText();
            String telefone = txtTelefone.getText();
            String cpf = txtTelefone.getText();
            int idade = Integer.parseInt(txtIdade.getText());
            dtm.setValueAt(nome, linhaSelecionada, 1);
            dtm.setValueAt(telefone, linhaSelecionada, 2);
            dtm.setValueAt(cpf, linhaSelecionada, 3);
            dtm.setValueAt(idade, linhaSelecionada, 4);
            transformaTabelaMatriz();
            gravaDadosArquivo();
        }
    }//GEN-LAST:event_bt_AlterarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastroClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastroClientes().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_Alterar;
    private javax.swing.JButton bt_Criar;
    private javax.swing.JButton bt_Excluir;
    private javax.swing.JButton bt_Salvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbCliente;
    private javax.swing.JTextField txtCpf;
    private javax.swing.JTextField txtIdade;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables

}

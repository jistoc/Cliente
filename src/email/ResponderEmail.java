/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import planilha.Planilha;
import sun.misc.IOUtils;

/**
 *
 * @author satna
 */
public class ResponderEmail extends javax.swing.JFrame {
    Message mensagem;
    MailApp mail;
    CaixaEntrada e;
    File arquivo;
    
    /**
     * Creates new form ResponderEmail
     message*/
    public ResponderEmail(Message mensagem, MailApp mail, CaixaEntrada e) throws MessagingException {
        initComponents();
        this.setLocationRelativeTo(null);
        this.mail=mail;
        this.e=e;
        this.mensagem=mensagem;
        File arquivo;
        
        txtAssunto.setText(mensagem.getSubject());
        txtData.setText(converteData(mensagem.getSentDate()));
        txtConteudo.setText(mail.processMessageBody(mensagem));
        Address[] f = mensagem.getFrom();
        txtDe.setText(f[0].toString());
        cTipoResposta.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent arg0) {
                lerArquivo();
            }
        });
        
        cTipoResposta.removeAllItems();
        cTipoResposta.addItem("Escolha um modelo...");
        
        arquivo = new File("src/respostas/");
        try {
            System.out.println(arquivo.getCanonicalPath());
        } catch (IOException ex) {
            Logger.getLogger(ResponderEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        File[] arqs = arquivo.listFiles();
        for(File a : arqs){
            cTipoResposta.addItem(a.getName());
        }
        
    }
    private void lerArquivo(){
        int r = cTipoResposta.getSelectedIndex();
        if(r!=-1){
            if(r==0){
                txtResposta.setText("");
            } else {
                try {
                    BufferedReader br = new BufferedReader(new FileReader("src/respostas/"+cTipoResposta.getItemAt(r)));
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = br.readLine();
                    }
                    txtResposta.setText(sb.toString());
                    br.close();
                } catch(Exception e){
                    e.printStackTrace();
                } 
            }
        }
    }
    private String converteData(Date data){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(data);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtDe = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        txtAssunto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtResposta = new javax.swing.JTextArea();
        cTipoResposta = new javax.swing.JComboBox<>();
        btnResponder = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtConteudo = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Responder E-mail");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("De:");

        jLabel2.setText("Assunto:");

        jLabel3.setText("Data:");

        txtDe.setEditable(false);

        txtData.setEditable(false);

        txtAssunto.setEditable(false);

        jLabel4.setText("Resposta:");

        txtResposta.setColumns(20);
        txtResposta.setRows(5);
        jScrollPane1.setViewportView(txtResposta);

        cTipoResposta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Teste", " " }));

        btnResponder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/send.gif"))); // NOI18N
        btnResponder.setText("Responder");
        btnResponder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResponderActionPerformed(evt);
            }
        });

        jLabel5.setText("Conteúdo:");

        txtConteudo.setEditable(false);
        txtConteudo.setColumns(20);
        txtConteudo.setRows(5);
        jScrollPane2.setViewportView(txtConteudo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnResponder))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtDe)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtAssunto)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1)
                                    .addComponent(cTipoResposta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(txtDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtAssunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cTipoResposta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnResponder)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResponderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResponderActionPerformed
        // TODO add your handling code here:
        if(txtResposta.getText().trim().equals("") || txtResposta.getText()==null ) {
            JOptionPane.showMessageDialog(this,"Mesagem vazia!", "Atenção", JOptionPane.WARNING_MESSAGE);
            
        } else {
            try {
                mail.responderEmail(mensagem, txtResposta.getText().trim());
                JOptionPane.showMessageDialog(this,"E-mail enviado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                mail.conectar();
                e.atualizar();
                e.re = false;
                //enviar uma mensagem para o servidor para acrescentar um email respondido
                
                
                this.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                e.re=true;
                JOptionPane.showMessageDialog(this,"Erro ao enviar o e-mail!", "Atenção", JOptionPane.WARNING_MESSAGE);
                
                try {
                    mensagem.setFlag(Flags.Flag.ANSWERED, false);
                } catch (MessagingException ex1) {
                    System.out.println("erro");
                }
            }
        }
    }//GEN-LAST:event_btnResponderActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:e
        e.re=false;
    }//GEN-LAST:event_formWindowClosing

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnResponder;
    private javax.swing.JComboBox<String> cTipoResposta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtAssunto;
    private javax.swing.JTextArea txtConteudo;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDe;
    private javax.swing.JTextArea txtResposta;
    // End of variables declaration//GEN-END:variables
}
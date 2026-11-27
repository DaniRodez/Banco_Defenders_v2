/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Banco;
import javax.swing.JOptionPane;


public class Deposito extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Deposito.class.getName());
    private controlador.Usuario usuarioActual;

    public Deposito(){
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public Deposito(controlador.Usuario usuario){
        this();
        this.usuarioActual = usuario;
        mostrarSaldo();
    }
    
    private void mostrarSaldo(){
        if (usuarioActual != null) {
            
            SaldoUsuario.setText(String.format("Saldo: $%.2f", usuarioActual.sueldo));
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Depositar = new javax.swing.JButton();
        Volver = new javax.swing.JButton();
        DepositoUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        SaldoUsuario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Deposito");

        Depositar.setText("Depositar");
        Depositar.addActionListener(this::DepositarActionPerformed);

        Volver.setText("Volver");
        Volver.addActionListener(this::VolverActionPerformed);

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel2.setText("Ingrese la cantidad a depositar");

        SaldoUsuario.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        SaldoUsuario.setText("Saldo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Depositar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Volver))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                                        .addComponent(DepositoUsuario))
                                    .addComponent(SaldoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 80, Short.MAX_VALUE)))))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(SaldoUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(DepositoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Depositar)
                    .addComponent(Volver))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void VolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolverActionPerformed
        MenuPrincipal menu = new MenuPrincipal(usuarioActual);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_VolverActionPerformed

    private void DepositarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DepositarActionPerformed
        if(usuarioActual == null){
            return;
        }
        
        String Monto = DepositoUsuario.getText().trim();
        if (Monto.isEmpty()){
            JOptionPane.showMessageDialog(this, "El campo esta vacio", "Valor no valido", JOptionPane.WARNING_MESSAGE);
        }
        
        double Valor;
        try{
            Valor = Double.parseDouble(Monto);
        }catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ingrese un numero Valido", "Valor no valido", JOptionPane.ERROR_MESSAGE);
            return;
            }
        
        if (Valor <= 0){
            JOptionPane.showMessageDialog(this, "La cantidad no puede ser 0 o menos", "Valor no valido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        usuarioActual.sueldo += Valor;
        controlador.guardarCambios();
        mostrarSaldo();
        DepositoUsuario.setText("");
        
        JOptionPane.showMessageDialog(this, "Se realizo el deposito. Los datos se actualizaran en breve", "Deposito exitoso", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_DepositarActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Deposito().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Depositar;
    private javax.swing.JTextField DepositoUsuario;
    private javax.swing.JLabel SaldoUsuario;
    private javax.swing.JButton Volver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}

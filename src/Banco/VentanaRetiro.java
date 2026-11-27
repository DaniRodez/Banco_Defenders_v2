/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Banco;
import javax.swing.JOptionPane;
/**
 *
 * @author ruben
 */
public class VentanaRetiro extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaRetiro.class.getName());
    private controlador.Usuario usuarioActual;

    public VentanaRetiro() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public VentanaRetiro(controlador.Usuario usuario) {
        this();
        this.usuarioActual = usuario;
        DatosUsuario();
    }

    private void DatosUsuario() {
        if (usuarioActual != null) {
            SaldoUsuario.setText(String.format("Saldo actual: $%.2f", usuarioActual.sueldo));
        }
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Retiro = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        SaldoUsuario = new javax.swing.JLabel();
        Volver = new javax.swing.JButton();
        Retirar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Retiro");

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Ingrese la cantidad a retirar");

        SaldoUsuario.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        SaldoUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SaldoUsuario.setText("Saldo: ");

        Volver.setText("volver");
        Volver.addActionListener(this::VolverActionPerformed);

        Retirar.setText("Retirar");
        Retirar.addActionListener(this::RetirarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SaldoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(Retiro, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(Retirar)
                            .addGap(101, 101, 101)
                            .addComponent(Volver))))
                .addGap(48, 48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SaldoUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Retiro, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Retirar)
                    .addComponent(Volver))
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RetirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RetirarActionPerformed
        if(usuarioActual == null){
            return;
        }
        
        String Monto = Retiro.getText().trim();
        if (Monto.isEmpty()){
            JOptionPane.showMessageDialog(this, "El campo esta vacio", "Datos no validos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        double Valor;
        try {
            Valor = Double.parseDouble(Monto);
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Ingrese un número válido.", "Valor no valido", JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        if (Valor <= 0) {
            JOptionPane.showMessageDialog(this,"La cantidad no puede ser 0 o menos.","Valor no Valido",JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        if (Valor > usuarioActual.sueldo) {
            JOptionPane.showMessageDialog(this,"Saldo insuficiente.","Valor no valido",JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        usuarioActual.sueldo -= Valor;
        controlador.guardarCambios();
        DatosUsuario();
        Retiro.setText("");
        
        JOptionPane.showMessageDialog(this,"Retiro exitoso. Los cambios se realizaran en breve", "Retiro exitoso", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_RetirarActionPerformed

    private void VolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VolverActionPerformed
        MenuPrincipal menu = new MenuPrincipal(usuarioActual);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_VolverActionPerformed

    public static void main(String args[]) {
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
        java.awt.EventQueue.invokeLater(() -> new VentanaRetiro().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Retirar;
    private javax.swing.JTextField Retiro;
    private javax.swing.JLabel SaldoUsuario;
    private javax.swing.JButton Volver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}

/**usuarioActual.sueldo = nuevoSueldo;
controlador.guardarCambios(); // Haz public static void guardarCambios() { guardarUsuarios(); } en controlador
actualizarDatosUsuario();**/

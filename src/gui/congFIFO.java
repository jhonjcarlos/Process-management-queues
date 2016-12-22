/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * congFIFO.java
 *
 * Created on 27-11-2014, 11:23:57 PM
 */

package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author carlos
 */
public class congFIFO extends javax.swing.JPanel {
    private JPanel panelBase;
    private JFrame ventana;
    /** Creates new form congFIFO */
    public congFIFO(JPanel pan, JFrame vt) {
        initComponents();

        this.setSize(pan.getSize());
        panelBase = pan;
        ventana = vt;
        deshabilitar();
    }

    public void deshabilitar(){
        jtxtTmpProc.setEditable(false);
        jtxtTmpProc.setText("");
    }

    public void habilitar(){
        jtxtTmpProc.setEditable(true);
    }

    public int darNroProcesos(){
        int res =0;
        res = jcmbNroProceso.getSelectedIndex();
        return res;
    }

    public String darTipoProcesado(){
        String res="";
        res = (String) jcmbTiempo.getSelectedItem();
        return res;
    }

    public int darTiempoProcesado(){
        int res =0;
        res = Integer.parseInt(jtxtTmpProc.getText());
        return res;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new org.edisoncor.gui.panel.Panel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcmbNroProceso = new org.edisoncor.gui.comboBox.ComboBoxRect();
        jcmbTiempo = new org.edisoncor.gui.comboBox.ComboBoxRect();
        jtxtTmpProc = new org.edisoncor.gui.textField.TextField();
        jLabel4 = new javax.swing.JLabel();

        setLayout(null);

        panel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/conf.jpg"))); // NOI18N

        jLabel1.setForeground(new java.awt.Color(-14336,true));
        jLabel1.setText("Configuraciones FIFO");

        jLabel2.setForeground(new java.awt.Color(-16776961,true));
        jLabel2.setText("Nro. Procesos:");

        jLabel3.setForeground(new java.awt.Color(-16776961,true));
        jLabel3.setText("Tiempo Procesamiento");

        jcmbNroProceso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "seleccione", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jcmbTiempo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "aleatorio", "constante" }));
        jcmbTiempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmbTiempoActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(-16776961,true));
        jLabel4.setText("Tiempo De Procesamiento");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap())
                    .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                            .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jtxtTmpProc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .addComponent(jcmbTiempo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jcmbNroProceso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                            .addGap(54, 54, 54)))))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addComponent(jcmbNroProceso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcmbTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(16, 16, 16)
                .addComponent(jtxtTmpProc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        add(panel1);
        panel1.setBounds(0, 0, 240, 310);
    }// </editor-fold>//GEN-END:initComponents

    private void jcmbTiempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmbTiempoActionPerformed
        // TODO add your handling code here:
        if (jcmbTiempo.getSelectedItem()=="constante") {
            habilitar();
        }
    }//GEN-LAST:event_jcmbTiempoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private org.edisoncor.gui.comboBox.ComboBoxRect jcmbNroProceso;
    private org.edisoncor.gui.comboBox.ComboBoxRect jcmbTiempo;
    private org.edisoncor.gui.textField.TextField jtxtTmpProc;
    private org.edisoncor.gui.panel.Panel panel1;
    // End of variables declaration//GEN-END:variables

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * confPrioridad.java
 *
 * Created on 28-11-2014, 12:58:33 PM
 */

package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author carlos
 */
public class confPrioridad extends javax.swing.JPanel {
    private JPanel panelBase;
    private JFrame ventana;
    /** Creates new form confPrioridad */
    public confPrioridad(JPanel pan, JFrame vt) {
        initComponents();
        this.setSize(pan.getSize());
        panelBase = pan;
        ventana = vt;
    }

    public int getNroProcesosPrioridad(){
        int res =0;
        res = jcmbNroProcesos.getSelectedIndex();
        return res;
    }

    public String getTipoColaPrioridad(){
        String res ="";
        res = (String) jcmbTipoCola.getSelectedItem();
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
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcmbNroProcesos = new org.edisoncor.gui.comboBox.ComboBoxRect();
        jcmbTipoCola = new org.edisoncor.gui.comboBox.ComboBoxRect();

        setLayout(null);

        jLabel1.setForeground(new java.awt.Color(-14336,true));
        jLabel1.setText("Configuraciones");

        jLabel2.setText("Nro. Procesos:");

        jLabel4.setText("Tiempo procesado");

        jLabel3.setForeground(new java.awt.Color(-14336,true));
        jLabel3.setText("Por Prioridad");

        jcmbNroProcesos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

        jcmbTipoCola.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecionar", "Ascendente", "Descendente" }));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcmbTipoCola, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcmbNroProcesos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))))))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(36, 36, 36)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jcmbNroProcesos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jcmbTipoCola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        add(panel1);
        panel1.setBounds(0, 0, 250, 320);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private org.edisoncor.gui.comboBox.ComboBoxRect jcmbNroProcesos;
    private org.edisoncor.gui.comboBox.ComboBoxRect jcmbTipoCola;
    private org.edisoncor.gui.panel.Panel panel1;
    // End of variables declaration//GEN-END:variables

}
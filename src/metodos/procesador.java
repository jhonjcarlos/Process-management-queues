/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metodos;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author carlos
 */
public class procesador extends JLabel{
    private boolean estado;

    private int tmpProceso;
    private JPanel panel;
    private int nroProcesoEjecutado;

    private JTable tabla;
    
    private DefaultTableModel modelo;

    private int tiempoPorcesFin;

    public procesador(int tmp, JPanel pan , JTable tbl) {
        tmpProceso = tmp;
        panel = pan;
        estado = false;
        nroProcesoEjecutado = 0;
        tabla = tbl;

        tiempoPorcesFin =0;

        modelo = (DefaultTableModel) tabla.getModel();

        this.setFont(new java.awt.Font("Tahoma", 0, 18));
        this.setForeground(Color.blue);
        this.setBounds(650, 155, 150, 100); // posicion de donde se graficara el procesado
        panel.add(this);
    }

    public int procesar(int tmpProc){
        int cont =0;
        int nvo = tmpProc;

        while (cont < tmpProceso) {
            try {
                nvo--;
                graficarConteo("procesando:"+nvo);
                System.out.println("tiempo de procesado: "+nvo+"\n");
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(procesador.class.getName()).log(Level.SEVERE, null, ex);
            }
            cont++;
            estado=true;
        }

        graficarConteo("terminado...");
        //limpiarProcesador();
        estado = false;
        return nvo;
    }

    
    public void graficarConteo(String txt){
        
        this.setText(txt);
    }

    public void limpiarProcesador(){
        this.setText("");
    }

    public String getConteo(){
        String res = "";
        res= this.getText();
        return res;
    }

    public boolean getEstado(){
        return estado;
    }

    public int getNroProcesoEjecutado(){
        return nroProcesoEjecutado;
    }

    public void setNroProcesoEjecutado(int nro){
        nroProcesoEjecutado = nro;
    }

    public int tiempoProcesadoProc(){
        return tmpProceso;
    }

    public void imprimirTabla(int ID, int tmpIni){
        //tiempoPorcesFin = tiempoPorcesFin + tmpProceso;
        String[] res ={"Proceso nro: "+ID, ""+tmpIni, ""+tmpProceso, ""+(tmpIni-tmpProceso),""+tiempoPorcesFin};
        modelo.addRow(res);
        tiempoPorcesFin = tiempoPorcesFin + tmpProceso;
    }

}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metodos;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author carlos
 */
public class cola extends JLabel{

    private JPanel panel;
    private ArrayList<proceso> lista;
    private boolean procesoAlProcesador;

    public cola(JPanel pan) {
        panel = pan;
        lista = new ArrayList<proceso>();
        this.setBounds(300, 130, 100, 100); // posicion de donde se graficara el la cola
        panel.add(this);
        procesoAlProcesador = false;
    }

    public void VerCola(){
        while (lista.size() > 0) {
            try {
                graficarNroProcesos();
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(cola.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public synchronized  void anadirProseso(proceso proc){
        lista.add(proc);
    }

    public proceso getPrimerProceso(){
        return lista.get(0);
    }

    public void eliminarPrimerProceso(){
        lista.remove(0);
    }

    public int tamanioCola(){
        return lista.size();
    }

    public void graficarNroProcesos(){
        this.setText(""+lista.size());
    }

    public void limpiarCola(){
        this.setText("");
    }

    public proceso darPrimerProcesoYEliminar(){
        return lista.remove(0);
    }

    public boolean getProcesoEnCanino(){
        return procesoAlProcesador;
    }

    public void setProcesoEnCamino(boolean est){
        procesoAlProcesador= est;
    }

     public proceso mayorCantidadProcesado(){
        proceso resultado = lista.get(0);
        for(int i=0;i<lista.size();i++)
        {
          if (lista.get(i).getTiempoVida() > resultado.getTiempoVida())
          {
             resultado = lista.get(i);
          }
        }
        return resultado;
     }

     public proceso menorCantidadProcesado(){
        proceso resultado = lista.get(0);
        for(int i=0;i<lista.size();i++)
        {
          if (lista.get(i).getTiempoVida() < resultado.getTiempoVida())
          {
             resultado = lista.get(i);
          }
        }
        return resultado;
     }

     public void eliminarProcesoDeLista(proceso proc){
         for (int i = 0; i < lista.size(); i++) {
             if (lista.get(i)== proc) {
                 lista.remove(i);
             }
         }
     }
}



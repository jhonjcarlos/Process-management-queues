/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package metodos;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author carlos
 */

public class proceso extends JLabel implements Runnable{
    private JPanel panel;
    private int posIniX,posIniY,posFinX,posFinY, tiempoVida;

    private procesador procesador;
    private cola listaPrep;
    private int ID;

    private cola listaBloqueados;

    private int ultimoProcesoRB;
    private boolean estadoEjecucion;

    private String[] impresionTabla;
    // constructor para el FIFO
    public proceso(JPanel p,int pX,int pY,int pXF,int pYF, int tmpVida, procesador proc, cola prep, int nro){
        this.panel = p;
        posIniX = pX;
        posIniY = pY;
        posFinX = pXF;
        posFinY = pYF;
        tiempoVida = tmpVida;
        procesador = proc;
        listaPrep = prep;
        ID = nro;

        this.setIcon(new ImageIcon(getClass().getResource("/img/023.png")));


        this.setBounds(posIniX, posIniY, 50, 50);
        //this.setBackground(Color.red);

        panel.add(this);

        // para que se ejecuite el prosesador


    }

    // constructor para RAUND ROBIN
    public proceso(JPanel p,int pX,int pY,int pXF,int pYF, int tmpVida, procesador proc, cola prep, int nro, cola Bloq, int nroUltimoProc){
        this.panel = p;
        posIniX = pX;
        posIniY = pY;
        posFinX = pXF;
        posFinY = pYF;
        tiempoVida = tmpVida;
        procesador = proc;
        listaPrep = prep;
        ID = nro;
        listaBloqueados = Bloq;
        ultimoProcesoRB = nroUltimoProc;
        
        estadoEjecucion = false;
        this.setIcon(new ImageIcon(getClass().getResource("/img/023.png")));


        this.setBounds(posIniX, posIniY, 50, 50);
        //this.setBackground(Color.red);

        panel.add(this);

        // para que se ejecuite el prosesador

    }


    public void run() {

        int res =0;

        moviendoEnXPositivo(posFinX);

        if(posIniX >= posFinX){
            System.out.println("proceso nro: "+ID+" anadiendo a la cola...\n");
            listaPrep.anadirProseso(this);

            System.out.println("tamanio de la cola es: "+listaPrep.tamanioCola()+"\n");
        }
        System.out.println("el estado del procesador es: "+procesador.getEstado()+"\n");

        
        if (listaPrep.tamanioCola() > 0) {

            //if (procesador.getEstado()== false) {

                System.out.println("este el ID del Proseco a moverse es: "+listaPrep.getPrimerProceso().getIdentificador()+"\n");
                
                if(listaPrep.darPrimerProcesoYEliminar().getIdentificador() == ID){

                    System.out.println("el estado del proceso caminado es: "+listaPrep.getProcesoEnCanino()+"\n");
                    //listaPrep.eliminarPrimerProceso();

                    //if (listaPrep.getProcesoEnCanino()==false) {
                        //listaPrep.setProcesoEnCamino(true);
                        System.out.println("tamanio de la cola despues de mi salida es: "+listaPrep.tamanioCola()+"\n");
                        moviendoEnXPositivo(570);
                        System.out.println("el proceso nro: "+ID+" es esta ejecutando..\n");

                            res = procesador.procesar(tiempoVida);

                            procesador.imprimirTabla(ID, tiempoVida);
                            // para cambiar el estado de ejecucion del proceso
                            procesador.setNroProcesoEjecutado(ID);
                            setEstadoEjecucionProceso(true);
                            listaPrep.setProcesoEnCamino(false);
                            System.out.println("el estado del proceso caminado despues de salir es: "+listaPrep.getProcesoEnCanino()+"\n");


                        //para salir del procesador
                        if (res == 0) {
                            moverAbajo(350);

                            // para que se teriren del procesador
                            if (posIniX >= posFinX) {
                                moviendoEnXPositivo(850);
                            }
                        }else{
                            // se va a lo cola de esperados
                            irColaRaundRobin(res);
                            regresarProcesadorRandRobin();
                        }
                }
            //}
            
        }
   
    }

    private void moverEnXDer(){
        int x=this.getX()+5;
        int y=this.getY()+0;
        posIniX=x;posIniY=y;
        this.setLocation(x,y);
    }

    private void moverEnXIzq(){
        int x=this.getX()-5;
        int y=this.getY()+0;
        posIniX=x;posIniY=y;
        this.setLocation(x,y);
    }

    private void moverEnYArriba(){
        int x=this.getX()+0;
        int y=this.getY()-5;
        posIniX=x;posIniY=y;
        this.setLocation(x,y);
    }

    private void moverEnYAbajo(){
        int x=this.getX()+0;
        int y=this.getY()+5;
        posIniX=x;posIniY=y;
        this.setLocation(x,y);
    }

    //===================== otro tipo de movimientos ==============================
    
    private void moviendoEnXPositivo(int disX) {//METO QUE MUEVE EN X
        while (posIniX<=disX) {
            moverEnXDer();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(proceso.class.getName()).log(Level.SEVERE, null, ex);
            }
            panel.repaint();
        }

    }

    private void moviendoEnXNegativo(int disX) {//METO QUE MUEVE EN X
        while (posIniX>=disX) {
            moverEnXIzq();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(proceso.class.getName()).log(Level.SEVERE, null, ex);
            }
            panel.repaint();
        }

    }

    private void moverArriba(int disY) {//METODO QUE SIRVE PARA RETIRARSE MOVIENDO PRIMERO arriba
        while(posIniY >= disY)
        {
            moverEnYArriba();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(proceso.class.getName()).log(Level.SEVERE, null, ex);
            }
            panel.repaint();
        }
    }

    private void moverAbajo(int disY) {//METODO QUE SIRVE PARA RETIRARSE MOVIENDO PRIMERO ABAJO
        while(posIniY<= disY)
        {
            moverEnYAbajo();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(proceso.class.getName()).log(Level.SEVERE, null, ex);
            }
            panel.repaint();
        }
    }

    // =========================fin del los metodos=========================================




    // para los procesos que se ejecutaran
    public int getTiempoVida(){
        return this.tiempoVida;
    }

    public void setTiempoVida(int tmp){
        this.tiempoVida = tmp;
    }

    public void ejecutarce(int tmpX){
        int res = 0;
        res = this.tiempoVida - tmpX;
        setTiempoVida(res);
    }

    public int getIdentificador(){
        return ID;
    }

    public boolean getEstadoEjecucionProceso(){
        return estadoEjecucion;
    }

    public void setEstadoEjecucionProceso(boolean est){
        estadoEjecucion = est;
    }

    public void irColaRaundRobin(int n){
        if (n>0) {
            listaBloqueados.anadirProseso(this);
            setTiempoVida(n);
            // para moverse a la cola
            moverArriba(90);
        }else{
            moverAbajo(350);
            // para que se teriren del procesador
            if (posIniX >= posFinX) {
                moviendoEnXPositivo(850);
            }
        }
    }


    public void regresarProcesadorRandRobin(){
        int res =0;

        if (listaBloqueados.tamanioCola() > 0 && listaPrep.tamanioCola()==0) {

            //if (procesador.getEstado()== false) {

                System.out.println("este el ID del Proceso a moverse de BLOQUEADOS es: "+listaBloqueados.getPrimerProceso().getIdentificador()+"\n");

                //if(listaBloqueados.darPrimerProcesoYEliminar().getIdentificador() == ID){

                    // para eliminar al primer elemento de la lista de bloqueados
                    listaBloqueados.eliminarPrimerProceso();

                    System.out.println("el estado del proceso BLOQUEADO caminado es: "+listaBloqueados.getProcesoEnCanino()+"\n");
                    //listaPrep.eliminarPrimerProceso();

                    //if (listaPrep.getProcesoEnCanino()==false) {
                        //listaPrep.setProcesoEnCamino(true);
                        System.out.println("tamanio de la cola BLOQUEADOS despues de mi salida es: "+listaBloqueados.tamanioCola()+"\n");

                        // para ejecutar los movimientos al rato de salir de la cola
                        moviendoEnXNegativo(350);

                        if (posIniX <= 350) {
                            moverAbajo(170);
                            if (posIniY >= 170) {
                                moviendoEnXPositivo(550);
                            }
                        }
                        System.out.println("el proceso nro: "+ID+" es esta ejecutando..\n");

                            res = procesador.procesar(tiempoVida);
                            // para el procesado em la tabla
                            procesador.imprimirTabla(ID, tiempoVida);

                            listaBloqueados.setProcesoEnCamino(false);
                            System.out.println("el estado del proceso BLOQUEADO caminado despues de salir es: "+listaBloqueados.getProcesoEnCanino()+"\n");


                        //para salir del procesador
                        if (res == 0) {
                            moverAbajo(350);

                            // para que se teriren del procesador
                            if (posIniX >= posFinX) {
                                moviendoEnXPositivo(850);
                            }
                        }else{
                            // se va a lo cola de esperados
                            irColaRaundRobin(res);
                            regresarProcesadorRandRobin();
                        }
                //}
            //}

        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package asosimulador;

import Graficos.GraficaBarras;
import Graficos.GraficaPastel;

/**
 *
 * @author carlos
 */
public class probar {

    public static void main(String[] args) {
        // TODO code application logic here

        int[] arrX ={10, 20, 30, 40};

        String[] arrY ={""+40, ""+10, ""+50, ""+5};
        GraficaBarras gb = new GraficaBarras();
        //gb.graficarbarras(arrX, arrY, "PROCESO", "TIEMPO DE PROCESADO", "POR FIFO");


        GraficaPastel pastel = new GraficaPastel();
        pastel.GraficaPastel(arrX, arrY, "GRAFICA DEL PASTE");
    }
}



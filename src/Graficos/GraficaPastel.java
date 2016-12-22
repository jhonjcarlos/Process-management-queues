/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class GraficaPastel {
    
    
    public void GraficaPastel(int []y,String [] x,String titulo)
    {
       DefaultPieDataset data = new DefaultPieDataset();
        for(int d=0;d<y.length;d++)
        {
         data.setValue(x[d],y[d]);  
        }
        JFreeChart chart = ChartFactory.createPieChart(titulo,data,true,true,true);
        ChartFrame frame=new ChartFrame(titulo,chart);
        frame.pack();
        frame.setVisible(true);
    }

    
    
}

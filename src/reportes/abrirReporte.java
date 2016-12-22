/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package reportes;

import javax.swing.JOptionPane;
import java.io.File;
/**
 *
 * @author carlos
 */
public class abrirReporte
{

    public abrirReporte()
    {

    }

    public void verPdf(File ruta)
    {
        try
        {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ruta +".pdf");
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

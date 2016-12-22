/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package reportes;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
/**
 *
 * @author carlos
 */
public class ImprecionTabla
{
    private File ruta_destino=null;
    private abrirReporte ver;

    public ImprecionTabla()
    {
        ver = new abrirReporte();
    }
    public void generarPdf(String titulOficl, String[] tit, JTable tab)
    {
        Colocar_Destino();

      if (this.ruta_destino!=null)
      {
          /*Declaramos documento como un objeto Document
      Asignamos el tamaño de hoja y los margenes */
    Document documento = new Document(PageSize.LETTER, 30, 30, 55, 55);
  /*  String html="<html><body>"
            + "</body></html>";
    documento.setHtmlStyleClass(html);
   *
   */

    //writer es declarado como el método utilizado para escribir en el archivo
    PdfWriter writer = null;

    try {
            try {
                //Obtenemos la instancia del archivo a utilizar
                writer = PdfWriter.getInstance(documento, new FileOutputStream(this.ruta_destino + ".pdf"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ImprecionTabla.class.getName()).log(Level.SEVERE, null, ex);
            }
    } catch (DocumentException ex) {
      //ex.getMessage();
    }

    //Agregamos un titulo al archivo
    documento.addTitle("Archivo pdf generado desde Java");

    //Agregamos el autor del archivo
    documento.addAuthor("Carlos");

    //Abrimos el documento para edición
    documento.open();

    //Declaramos un texto como Paragraph
    //Le podemos dar formado como alineación, tamaño y color a la fuente.
    Paragraph parrafo = new Paragraph();
    parrafo.setAlignment(Paragraph.ALIGN_CENTER);
    parrafo.setFont(FontFactory.getFont("Sans",20,Font.BOLD, BaseColor.BLUE));
    parrafo.add(titulOficl);
    //parrafo.add("Garabatos Linux");

    try {
      //Agregamos el texto al documento
      documento.add(parrafo);

      //Agregamos un salto de linea
      documento.add(new Paragraph(" "));

      //Agregamos la tabla al documento haciendo
      //la llamada al método tabla()
      documento.add(tabla(tit, tab));
      int mensaje = JOptionPane.showConfirmDialog(null, "Archivo PDF creado Correctamente...\n Desea ver el archivo?");
      //JOptionPane.showMessageDialog(null, "Archivo PDF creado Correctamente...");
     if(mensaje == 0)
     {
        ver.verPdf(ruta_destino);
     }
     else
     {

     }
      
    } catch (DocumentException ex) {
      ex.getMessage();
    }

    documento.close(); //Cerramos el documento
    writer.close(); //Cerramos writer

      }
    }
    public void Colocar_Destino()
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo PDF","pdf","PDF");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
            if ( result == JFileChooser.APPROVE_OPTION )
            {
                this.ruta_destino = fileChooser.getSelectedFile().getAbsoluteFile();
            }
   }

    //Método para crear la tabla
  // pero esta ves no usare esto porque recibire un tabla desde el exterior ocea de otro metodo
  public static PdfPTable tabla(String[] tit, JTable tbl)
  {
      int fila =0;
    //Instanciamos una tabla de n columnas
    int n = tit.length;
    PdfPTable tabla = new PdfPTable(n);

      for (int i = 0; i < tit.length; i++)
      {
          // agregmos los titulos a la tabla
          tabla.addCell(tit[i]);

      }
      
      for (fila = 0; fila < tbl.getRowCount(); fila++)
      {
              for (int i = 0; i < n; i++)
              {
                  //agrgamos los campos en forma de strings a la tabla creada por el pdf
                tabla.addCell(String.valueOf(tbl.getValueAt(fila, i)));
              }

      }
    return tabla;
  }

}

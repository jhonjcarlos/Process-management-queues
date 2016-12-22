/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class generadorPDF {
    private File ruta_destino=null;
    public generadorPDF(){ 
    }
    /* metodo que hace uso de la clase itext para manipular archivos PDF*/
    public void crear_PDF(String t, String a, String s, String k, String c){
        //abre ventana de dialogo "guardar"
        Colocar_Destino();
        //si destino es diferente de null
        if(this.ruta_destino!=null){
            try {
                // se crea instancia del documento
                Document mipdf = new Document() {};
                // se establece una instancia a un documento pdf
                PdfWriter.getInstance(mipdf, new FileOutputStream(this.ruta_destino + ".pdf"));
                mipdf.open();// se abre el documento 
                mipdf.addTitle(t); // se añade el titulo 
                mipdf.addAuthor(a); // se añade el autor del documento
                mipdf.addSubject(s); //se añade el asunto del documento
                mipdf.addKeywords(k); //Se agregan palabras claves  
                mipdf.add(new Paragraph(c)); // se añade el contendio del PDF
                mipdf.close(); //se cierra el PDF&
                JOptionPane.showMessageDialog(null,"Documento PDF creado");
                } catch (DocumentException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                         } 
            } 
        }
    /* abre la ventana de dialogo GUARDAR*/
    public void Colocar_Destino(){
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo PDF","pdf","PDF");
        JFileChooser fileChooser = new JFileChooser(); 
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(null);
        if ( result == JFileChooser.APPROVE_OPTION ){ 
            this.ruta_destino = fileChooser.getSelectedFile().getAbsoluteFile();
            }
        }
}

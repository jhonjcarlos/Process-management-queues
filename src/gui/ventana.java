/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ventana.java
 *
 * Created on 26-11-2014, 06:01:57 PM
 */

package gui;

import Graficos.GraficaBarras;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import metodos.cola;
import metodos.procesador;
import metodos.proceso;
import reportes.ImprecionTabla;

/**
 *
 * @author carlos
 */
public class ventana extends javax.swing.JFrame {
    private congFIFO panelConfFifo;

    private  confRaundRobin panelConfRaundRb;

    private confSigProc panelConfSigProc;

    private confPrioridad panelConfPriorida;

    private confAltoIndice panelConfAltoIndice;

    private cola colaPreparados;

    private cola colaBloqueados;

    private int NroId;

    private tablaFifo tablaFIFO;

    private cola colaPrioridad;

    private cola colaSigProceso;

    private int tiempoFinal;

    private DefaultTableModel modelo;


    private int tiempoProcessSFT;
    private int tiempoProcessPriori;

    /** Creates new form ventana */
    public ventana() {
        initComponents();

        this.setLocationRelativeTo(null);

        panelConfFifo = new congFIFO(jpanelBaseConf, this);

        panelConfRaundRb = new confRaundRobin(jpanelBaseConf, this);

        panelConfSigProc = new confSigProc(jpanelBaseConf, this);

        panelConfPriorida = new confPrioridad(jpanelBaseConf, this);

        panelConfAltoIndice = new confAltoIndice(jpanelBaseConf, this);

        tiempoFinal =0;

        deshabilitarBanner();

        colaPreparados = new cola(jpanelBase);
        colaBloqueados = new cola(jpanelBase);
        colaPrioridad = new cola(jpanelBase);
        colaSigProceso = new cola(jpanelBase);

        NroId =0;

        tiempoProcessSFT =0;
        tiempoProcessPriori=0;

        tablaFIFO = new tablaFifo(null, true);

        modelo = (DefaultTableModel) jtblReport.getModel();
        elimarContenidoTabla();

    }

    private void cargarPanelConfFIFO(){
        jpanelBaseConf.removeAll();
        jpanelBaseConf.add(panelConfFifo);
        jpanelBaseConf.repaint();
        this.pack();
    }


    private  void cargarPanelConfRaundRb(){
        jpanelBaseConf.removeAll();
        jpanelBaseConf.add(panelConfRaundRb);
        jpanelBaseConf.repaint();
        this.pack();
    }

    private  void cargarPanelConfSigProc(){
        jpanelBaseConf.removeAll();
        jpanelBaseConf.add(panelConfSigProc);
        jpanelBaseConf.repaint();
        this.pack();
    }

    private  void cargarPanelConfPrioridad(){
        jpanelBaseConf.removeAll();
        jpanelBaseConf.add(panelConfPriorida);
        jpanelBaseConf.repaint();
        this.pack();
    }

    private  void cargarPanelConfAltoIndice(){
        jpanelBaseConf.removeAll();
        jpanelBaseConf.add(panelConfAltoIndice);
        jpanelBaseConf.repaint();
        this.pack();
    }

    public void cargarSeleccionarConf(){
        jpanelBaseConf.removeAll();
        jpanelBaseConf.repaint();
        this.pack();
    }

    private void habilitarBanner(){
        jbtnIniciar.setEnabled(true);
        jbtnVetTabla.setEnabled(true);
    }

    private void deshabilitarBanner(){
        jbtnIniciar.setEnabled(false);
        jbtnVetTabla.setEnabled(false);
    }

    public int randonizar(int lim){
        int res = 0;
        res = (int) (Math.random()*lim);
        //System.out.println("RANGO = "+res);
        if (res <= 0) {
            res = randonizar(lim);
            
        }
        return res;
    }


    public void elimarContenidoTabla(){
        while(modelo.getRowCount()>0){
            modelo.removeRow(0);
        }
    }

    public void anadirTitulosTabla(String[] titulos){
        JTableHeader th;
        th = jtblReport.getTableHeader();
        Font fuente = new Font("Arial", Font.BOLD, 16);

        th.setFont(fuente);
        modelo.setColumnIdentifiers(titulos);
        
    }

    
    public void crearProcesosFifo(int nro, int tiempoProcesado){
        procesador proc = new procesador(tiempoProcesado,jpanelBase, jtblReport);
        int cant = 0;
        while (cant < nro) {

            try {

                //procesador proc = new procesador(tiempoProcesado,jpanelBase);
                Thread hiloProceso=new Thread(new proceso(jpanelBase, 10, 170, 300, 170, tiempoProcesado, proc, colaPreparados, NroId));
                hiloProceso.start();
                System.out.println("este es el fifo");
                String[] dato ={"proceso nro. "+NroId,""+tiempoProcesado,""+tiempoProcesado,""+tiempoFinal}; // tiempo final de procesado (tiempoProcesado*NroId)
                anadirDatosFifo(dato);

                tiempoFinal = tiempoFinal+ tiempoProcesado;

                Thread.sleep(randonizar(10)*100);

            } catch (InterruptedException ex) {
                Logger.getLogger(ventana.class.getName()).log(Level.SEVERE, null, ex);
            }

            NroId=NroId+1;
            cant++;

        }
    }

    public void crearProcesosRaundRobin(int nro, int tiempoProcesado, int tiempoVidaProc){
        procesador proc = new procesador(tiempoProcesado,jpanelBase, jtblReport);
        int cant = 0;
        while (cant < nro) {

            try {

                //procesador proc = new procesador(tiempoProcesado,jpanelBase);
                Thread hiloProceso=new Thread(new proceso(jpanelBase, 10, 170, 300, 170, tiempoVidaProc, proc, colaPreparados, NroId, colaBloqueados, panelConfRaundRb.darNroProcesosRaundRobin()-1));
                hiloProceso.start();
                System.out.println("este es el RAUND ROBIN");
                Thread.sleep(randonizar(10)*100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ventana.class.getName()).log(Level.SEVERE, null, ex);
            }

            NroId=NroId+1;
            cant++;

        }
    }

    public void crearProcesosPrioridad(int nro, int tiempoProcesado){
        procesador proc = new procesador(tiempoProcesado,jpanelBase, jtblReport);

        for (int i = 0; i < nro; i++) {
            System.out.println("creando proceso para la lista proceso nro: "+NroId+"\n");
            colaPrioridad.anadirProseso(new proceso(jpanelBase, 10, 170, 300, 170, tiempoProcesado, proc, colaPreparados, NroId));
            NroId=NroId+1;
        }
        
        int cant = 0;
        while (cant < nro) {
            System.out.println("entro al while\n");
            try {
                if (panelConfPriorida.getTipoColaPrioridad()=="Ascendente") {
                    System.out.println("este es el Priorida Ascendente");
                    proceso procss = colaPrioridad.mayorCantidadProcesado();
                    colaPrioridad.eliminarProcesoDeLista(procss);
                    Thread hiloProceso=new Thread(procss);
                    hiloProceso.start();
                    //System.out.println("este es el Priorida Ascendente");
                }

                if (panelConfPriorida.getTipoColaPrioridad()=="Descendente") {
                    System.out.println("este es el Priorida Descendente");
                    proceso procss = colaPrioridad.menorCantidadProcesado();
                    colaPrioridad.eliminarProcesoDeLista(procss);
                    Thread hiloProceso=new Thread(procss);
                    hiloProceso.start();
                    //System.out.println("este es el Priorida Descendente");
                }

                //String[] dato ={"proceso nro. "+NroId,""+tiempoProcesado,""+tiempoProcesado,""+(tiempoProcesado*NroId)};
                //anadirDatosFifo(dato);

                Thread.sleep(randonizar(10)*100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ventana.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            cant++;

        }
    }

    public void correrPrimerProcesoSigProceso(int tiempoProcesado){
        try {
            procesador proc = new procesador(tiempoProcesado,jpanelBase, jtblReport);
            Thread hiloProceso=new Thread(new proceso(jpanelBase, 10, 170, 300, 170, tiempoProcesado, proc, colaPreparados, NroId));
            hiloProceso.start();
            System.out.println("se ejecuto el primer proceso...\n");
            Thread.sleep(randonizar(10) * 100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void crearProcesosSiguienteProc(int nro, int tiempoProcesado){
        procesador proc = new procesador(tiempoProcesado,jpanelBase, jtblReport);

        for (int i = 0; i < nro-1; i++) {
            System.out.println("creando proceso para la lista proceso nro: "+NroId+"\n");
            colaSigProceso.anadirProseso(new proceso(jpanelBase, 10, 170, 300, 170, tiempoProcesado, proc, colaPreparados, NroId));
            NroId=NroId+1;
        }

        int cant = 0;
        while (cant < (nro-1)) {
            System.out.println("entro al while\n");
            try {
                if (panelConfSigProc.getProximoProceso()=="mas largo") {
                    System.out.println("este es el siguiente proseso mas largo");
                    proceso procss = colaSigProceso.mayorCantidadProcesado();
                    colaSigProceso.eliminarProcesoDeLista(procss);
                    Thread hiloProceso=new Thread(procss);
                    hiloProceso.start();
                    //System.out.println("este es el Priorida Ascendente");
                }

                if (panelConfSigProc.getProximoProceso()=="mas corto") {
                    System.out.println("este es el proceso mas corto");
                    proceso procss = colaSigProceso.menorCantidadProcesado();
                    colaSigProceso.eliminarProcesoDeLista(procss);
                    Thread hiloProceso=new Thread(procss);
                    hiloProceso.start();
                    //System.out.println("este es el Priorida Descendente");
                }

                //String[] dato ={"proceso nro. "+NroId,""+tiempoProcesado,""+tiempoProcesado,""+(tiempoProcesado*NroId)};
                //anadirDatosFifo(dato);

                Thread.sleep(randonizar(10)*100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ventana.class.getName()).log(Level.SEVERE, null, ex);
            }
            cant++;
        }
    }

    private int darTiempoPorcesadoFIFO(){
        // para el FIFO
        int res = 0;
        if (panelConfFifo.darTipoProcesado()=="constante") {
            res = panelConfFifo.darTiempoProcesado();
        }

        if (panelConfFifo.darTipoProcesado()=="aleatorio") {
            res = randonizar(100);
        }
        return res;
    }

    private int darTiempoProsadoRANDRB(){
        int res =0;
        // para el RAUND ROBIN
        if (panelConfRaundRb.tipoProcesadoRaundRobin()=="aleatorio") {
            res = randonizar(100);
        }
        if (panelConfRaundRb.tipoProcesadoRaundRobin()=="constante") {
            res = panelConfRaundRb.darTiempoProcesadoRaundRobin();
        }
        return res;
    }

    private int darTiempoProcesadoPrioridad(){
        int res =0;
        res = randonizar(100);
        return res;
    }

    // para hacer los graficos de la simulacion

    private void graficarFifo(){

        int[] arrX = new int[panelConfFifo.darNroProcesos()];
        
        String[] arrY = new String[panelConfFifo.darNroProcesos()];
        for (int i = 0; i < arrY.length; i++) {
            arrY[i]= ""+i;
            arrX[i]=panelConfFifo.darTiempoProcesado();
        }

        GraficaBarras gb = new GraficaBarras();
        gb.graficarbarras(arrX, arrY, "PROCESOS", "TIEMPO DE PROCESADO", "GRAFICO DEL PROCASADO FIFO");
    }

    private void graficarRaundRobin(){

        int[] arrX = new int[panelConfRaundRb.darNroProcesosRaundRobin()];

        String[] arrY = new String[panelConfRaundRb.darNroProcesosRaundRobin()];
        for (int i = 0; i < arrY.length; i++) {
            arrY[i]= ""+i;
            arrX[i]=panelConfRaundRb.darTiempoProcesadoRaundRobin();
        }

        GraficaBarras gb = new GraficaBarras();
        gb.graficarbarras(arrX, arrY, "PROCESOS", "TIEMPO DE PROCESADO", "GRAFICO DEL PROCASADO RAUND ROBIN");
    }

    private void graficarSiguienteProceso(){

        int[] arrX = new int[panelConfSigProc.getNroProcesos()];

        String[] arrY = new String[panelConfSigProc.getNroProcesos()];
        for (int i = 0; i < arrY.length; i++) {
            arrY[i]= ""+i;
            arrX[i]=tiempoProcessSFT;
        }

        GraficaBarras gb = new GraficaBarras();
        gb.graficarbarras(arrX, arrY, "PROCESOS", "TIEMPO DE PROCESADO", "GRAFICO DEL PROCASADO SIG. PROCESO");
    }

    private void graficarPrioridad(){

        int[] arrX = new int[panelConfPriorida.getNroProcesosPrioridad()];

        String[] arrY = new String[panelConfPriorida.getNroProcesosPrioridad()];
        for (int i = 0; i < arrY.length; i++) {
            arrY[i]= ""+i;
            arrX[i]=tiempoProcessPriori;
        }

        GraficaBarras gb = new GraficaBarras();
        gb.graficarbarras(arrX, arrY, "PROCESOS", "TIEMPO DE PROCESADO", "GRAFICO DEL PROCASADO POR PRIORIDAD");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpanelBanner = new org.edisoncor.gui.panel.Panel();
        jbtnIniciar = new javax.swing.JButton();
        jSliderVelocidad = new javax.swing.JSlider();
        jbtnVetTabla = new javax.swing.JButton();
        jbtnImprimir = new javax.swing.JButton();
        jbtnVerGrafico = new javax.swing.JButton();
        jpanelConf = new org.edisoncor.gui.panel.Panel();
        jcmbSeleccion = new org.edisoncor.gui.comboBox.ComboBoxRect();
        jLabel1 = new javax.swing.JLabel();
        jpanelBase = new org.edisoncor.gui.panel.Panel();
        jpanelBaseConf = new org.edisoncor.gui.panel.Panel();
        jpanelTabla = new org.edisoncor.gui.panel.Panel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblReport = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtxtNroSoud = new org.edisoncor.gui.textField.TextField();
        jtxtTmpSoud = new org.edisoncor.gui.textField.TextField();
        jtxtNroCD = new org.edisoncor.gui.textField.TextField();
        jtxtNroPrint = new org.edisoncor.gui.textField.TextField();
        jtxtTmpCD = new org.edisoncor.gui.textField.TextField();
        jtxtTmpPrint = new org.edisoncor.gui.textField.TextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setResizable(false);

        jpanelBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/banner.jpg"))); // NOI18N

        jbtnIniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Play64.png"))); // NOI18N
        jbtnIniciar.setBorderPainted(false);
        jbtnIniciar.setContentAreaFilled(false);
        jbtnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnIniciarActionPerformed(evt);
            }
        });

        jSliderVelocidad.setFont(new java.awt.Font("Tahoma", 0, 8));
        jSliderVelocidad.setMajorTickSpacing(100);
        jSliderVelocidad.setMaximum(1000);
        jSliderVelocidad.setMinimum(100);
        jSliderVelocidad.setPaintLabels(true);
        jSliderVelocidad.setPaintTicks(true);
        jSliderVelocidad.setValue(1000);
        jSliderVelocidad.setBorder(javax.swing.BorderFactory.createTitledBorder("VELOCIDAD"));

        jbtnVetTabla.setText("Ver Tabla");
        jbtnVetTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnVetTablaActionPerformed(evt);
            }
        });

        jbtnImprimir.setText("Imprimir tabla");
        jbtnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnImprimirActionPerformed(evt);
            }
        });

        jbtnVerGrafico.setText("Ver Grafico");
        jbtnVerGrafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnVerGraficoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpanelBannerLayout = new javax.swing.GroupLayout(jpanelBanner);
        jpanelBanner.setLayout(jpanelBannerLayout);
        jpanelBannerLayout.setHorizontalGroup(
            jpanelBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelBannerLayout.createSequentialGroup()
                .addContainerGap(257, Short.MAX_VALUE)
                .addComponent(jbtnVerGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnVetTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSliderVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        jpanelBannerLayout.setVerticalGroup(
            jpanelBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelBannerLayout.createSequentialGroup()
                .addGroup(jpanelBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelBannerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSliderVelocidad, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelBannerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbtnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpanelBannerLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jpanelBannerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnVetTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(jbtnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)))
                    .addGroup(jpanelBannerLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jbtnVerGrafico, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jcmbSeleccion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "FIFO", "Raund Robin", "Siguiente Proceso", "Prioridad" }));
        jcmbSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmbSeleccionActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(-65536,true));
        jLabel1.setText("Elija Una Opcion");

        javax.swing.GroupLayout jpanelConfLayout = new javax.swing.GroupLayout(jpanelConf);
        jpanelConf.setLayout(jpanelConfLayout);
        jpanelConfLayout.setHorizontalGroup(
            jpanelConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelConfLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(636, 636, 636))
            .addGroup(jpanelConfLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jcmbSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpanelConfLayout.setVerticalGroup(
            jpanelConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelConfLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jcmbSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jpanelBase.setColorPrimario(new java.awt.Color(-16711936,true));
        jpanelBase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/elFondo.png"))); // NOI18N

        javax.swing.GroupLayout jpanelBaseLayout = new javax.swing.GroupLayout(jpanelBase);
        jpanelBase.setLayout(jpanelBaseLayout);
        jpanelBaseLayout.setHorizontalGroup(
            jpanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 845, Short.MAX_VALUE)
        );
        jpanelBaseLayout.setVerticalGroup(
            jpanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jpanelBaseConfLayout = new javax.swing.GroupLayout(jpanelBaseConf);
        jpanelBaseConf.setLayout(jpanelBaseConfLayout);
        jpanelBaseConfLayout.setHorizontalGroup(
            jpanelBaseConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 221, Short.MAX_VALUE)
        );
        jpanelBaseConfLayout.setVerticalGroup(
            jpanelBaseConfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );

        jtblReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtblReport);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/audio.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/grabar.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N

        jLabel5.setText("Nro:");

        jLabel6.setText("Tmp:");

        javax.swing.GroupLayout jpanelTablaLayout = new javax.swing.GroupLayout(jpanelTabla);
        jpanelTabla.setLayout(jpanelTablaLayout);
        jpanelTablaLayout.setHorizontalGroup(
            jpanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelTablaLayout.createSequentialGroup()
                .addGroup(jpanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanelTablaLayout.createSequentialGroup()
                        .addContainerGap(19, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jpanelTablaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jpanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jtxtTmpSoud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtNroSoud, 0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jpanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtTmpCD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtNroCD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jpanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jtxtNroPrint, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                    .addComponent(jtxtTmpPrint, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpanelTablaLayout.setVerticalGroup(
            jpanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jpanelTablaLayout.createSequentialGroup()
                        .addGroup(jpanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jpanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtNroSoud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtNroCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtNroPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jpanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jtxtTmpSoud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtxtTmpCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtxtTmpPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpanelBaseConf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpanelConf, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpanelBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jpanelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelBanner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpanelConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpanelBaseConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jpanelBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpanelTabla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnIniciarActionPerformed

        int tiempoProcesado = 0;

        if (jcmbSeleccion.getSelectedItem()=="FIFO") {

            elimarContenidoTabla();
            String[] titulos ={"Proceso", "Tiempo Inicio", "Tiempo Procesado", "Tiempo Salida", "Tiempo Espera"};
            anadirTitulosTabla(titulos);

            tiempoProcesado=darTiempoPorcesadoFIFO();
            //procesador proc = new procesador(tiempoProcesado,jpanelBase);
            crearProcesosFifo(panelConfFifo.darNroProcesos(), tiempoProcesado);
        }

        if (jcmbSeleccion.getSelectedItem()=="Raund Robin") {

            elimarContenidoTabla();
            String[] titulos ={"Proceso", "Tiempo Inicio", "Tiempo Procesado", "Tiempo Salida", "Tiempo Espera"};
            anadirTitulosTabla(titulos);
            
            tiempoProcesado=darTiempoProsadoRANDRB();
            //procesador proc = new procesador(tiempoProcesado,jpanelBase);
            crearProcesosRaundRobin(panelConfRaundRb.darNroProcesosRaundRobin(), panelConfRaundRb.darTiempoProcesadoQuantumRaundRobin(), tiempoProcesado);
        }

        if (jcmbSeleccion.getSelectedItem()=="Prioridad") {

            elimarContenidoTabla();
            String[] titulos ={"Proceso", "Tiempo Inicio", "Tiempo Procesado", "Tiempo Salida", "Tiempo Espera"};
            anadirTitulosTabla(titulos);
            tiempoProcesado=darTiempoProcesadoPrioridad();
            tiempoProcessPriori = tiempoProcesado;
            //procesador proc = new procesador(tiempoProcesado,jpanelBase);
            crearProcesosPrioridad(panelConfPriorida.getNroProcesosPrioridad(), tiempoProcesado);
        }

        if (jcmbSeleccion.getSelectedItem()=="Siguiente Proceso") {

            elimarContenidoTabla();
            String[] titulos ={"Proceso", "Tiempo Inicio", "Tiempo Procesado", "Tiempo Salida", "Tiempo Espera"};
            anadirTitulosTabla(titulos);
            tiempoProcesado=darTiempoProcesadoPrioridad();
            tiempoProcessSFT = tiempoProcesado;
            //procesador proc = new procesador(tiempoProcesado,jpanelBase);
            correrPrimerProcesoSigProceso(tiempoProcesado);
            crearProcesosSiguienteProc(panelConfSigProc.getNroProcesos(), tiempoProcesado);
        }

    }//GEN-LAST:event_jbtnIniciarActionPerformed

    private void jcmbSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmbSeleccionActionPerformed
        // TODO add your handling code here:
        if (jcmbSeleccion.getSelectedItem()=="FIFO") {
            cargarPanelConfFIFO();
            habilitarBanner();
        }
        if (jcmbSeleccion.getSelectedItem()=="Seleccionar") {
            cargarSeleccionarConf();
        }
        if (jcmbSeleccion.getSelectedItem()=="Raund Robin") {
            cargarPanelConfRaundRb();
            habilitarBanner();
        }

        if (jcmbSeleccion.getSelectedItem()=="Siguiente Proceso") {
            cargarPanelConfSigProc();
            habilitarBanner();
        }

        if (jcmbSeleccion.getSelectedItem()=="Prioridad") {
            cargarPanelConfPrioridad();
            habilitarBanner();
        }
        
    }//GEN-LAST:event_jcmbSeleccionActionPerformed

    private void jbtnVetTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnVetTablaActionPerformed
        // TODO add your handling code here:
        if (jcmbSeleccion.getSelectedItem()=="FIFO") {
            String[] titulos ={"Proceso","Tiempo Requerido","Tiempo Procesado","Tiempo Esperado"};

            tablaFIFO.anadirTitulosTabla(titulos, "TABLA DE DATOS FIFO");

            tablaFIFO.setVisible(true);
        }

        if (jcmbSeleccion.getSelectedItem()=="Raund Robin") {
            String[] titulos ={"Proceso","Tiempo Requerido","Tiempo Procesado","Tiempo Salida","Tiempo Esperado"};

            tablaFIFO.anadirTitulosTabla(titulos, "TABLA DE DATOS RAUND ROBIN");

            tablaFIFO.setVisible(true);
        }
    }//GEN-LAST:event_jbtnVetTablaActionPerformed

    private void jbtnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnImprimirActionPerformed
        // TODO add your handling code here:
        if (jcmbSeleccion.getSelectedItem()=="FIFO") {
            String[] titulos ={"Proceso", "Tiempo Inicio", "Tiempo Procesado", "Tiempo Salida", "Tiempo Espera"};
            ImprecionTabla imp = new ImprecionTabla();
            imp.generarPdf("TABLA DE REPORTES DEL PROCESADO FIFO", titulos, jtblReport);
        }

        if (jcmbSeleccion.getSelectedItem()=="Raund Robin") {
            String[] titulos ={"Proceso", "Tiempo Inicio", "Tiempo Procesado", "Tiempo Salida", "Tiempo Espera"};
            ImprecionTabla imp = new ImprecionTabla();
            imp.generarPdf("TABLA DE REPORTES DEL PROCESADO RAUND ROBIN", titulos, jtblReport);
        }

        if (jcmbSeleccion.getSelectedItem()=="Siguiente Proceso") {
            String[] titulos ={"Proceso", "Tiempo Inicio", "Tiempo Procesado", "Tiempo Salida", "Tiempo Espera"};
            ImprecionTabla imp = new ImprecionTabla();
            imp.generarPdf("TABLA DE REPORTES DEL PROCESADO DEL SIGUIENTE PROCESO", titulos, jtblReport);
        }

        if (jcmbSeleccion.getSelectedItem()=="Prioridad") {
            String[] titulos ={"Proceso", "Tiempo Inicio", "Tiempo Procesado", "Tiempo Salida", "Tiempo Espera"};
            ImprecionTabla imp = new ImprecionTabla();
            imp.generarPdf("TABLA DE REPORTES DEL PROCESADO POR PRIORIDAD", titulos, jtblReport);
        }

    }//GEN-LAST:event_jbtnImprimirActionPerformed

    private void jbtnVerGraficoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnVerGraficoActionPerformed
        // TODO add your handling code here:
        if (jcmbSeleccion.getSelectedItem()=="FIFO") {
            graficarFifo();
        }

        if (jcmbSeleccion.getSelectedItem()=="Raund Robin") {
            graficarRaundRobin();
        }

        if (jcmbSeleccion.getSelectedItem()=="Siguiente Proceso") {
            graficarSiguienteProceso();
        }

        if (jcmbSeleccion.getSelectedItem()=="Prioridad") {
            graficarPrioridad();
        }
    }//GEN-LAST:event_jbtnVerGraficoActionPerformed

    public void anadirDatosFifo(String[] cad){
        tablaFIFO.anadirDatosTabla(cad);
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSliderVelocidad;
    private javax.swing.JButton jbtnImprimir;
    private javax.swing.JButton jbtnIniciar;
    private javax.swing.JButton jbtnVerGrafico;
    private javax.swing.JButton jbtnVetTabla;
    private org.edisoncor.gui.comboBox.ComboBoxRect jcmbSeleccion;
    private org.edisoncor.gui.panel.Panel jpanelBanner;
    private org.edisoncor.gui.panel.Panel jpanelBase;
    private org.edisoncor.gui.panel.Panel jpanelBaseConf;
    private org.edisoncor.gui.panel.Panel jpanelConf;
    private org.edisoncor.gui.panel.Panel jpanelTabla;
    private javax.swing.JTable jtblReport;
    private org.edisoncor.gui.textField.TextField jtxtNroCD;
    private org.edisoncor.gui.textField.TextField jtxtNroPrint;
    private org.edisoncor.gui.textField.TextField jtxtNroSoud;
    private org.edisoncor.gui.textField.TextField jtxtTmpCD;
    private org.edisoncor.gui.textField.TextField jtxtTmpPrint;
    private org.edisoncor.gui.textField.TextField jtxtTmpSoud;
    // End of variables declaration//GEN-END:variables

}

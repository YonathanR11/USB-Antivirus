package Antivirus;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Yonathan
 */
public class Antivirus extends javax.swing.JFrame implements Runnable{

    static boolean VarInfo;
    static boolean VarInfo2;

    /**
     * Creates new form Antivirus
     */
    
    int x;
    int y;
    int PosicionMouse;
    boolean b = true;
    private boolean minimiza = false;
    public static boolean analizando = false;
    public static File f1 = new File("");
    public static File f2 = new File("");
    public static File f3 = new File("");
    public static File f4 = new File("");
    public static File f5 = new File("");
    public static File f6 = new File("");
    public static File[] unidades;
    Object[] items;
    public static String ruta = "";
    public static String uniSelec;
    String[] letters;
    File[] drives;
    boolean[] isDrive;
    Thread hilo;
    private String s1;
    private String s2;
    private Object[] i;
    long start=-1,stop=-1;
    String abs22;
    
    public Antivirus() {
        initComponents();
        hilo = new Thread(this);
        this.setLocationRelativeTo(null);
        buscarUnidades();
        UnidadesVacunar();
        setIconImage(new ImageIcon(getClass().getResource("/img/logo.png")).getImage());
        Fondo.removeAll();
        Fondo.repaint();
        Fondo.revalidate();
        Fondo.add(Inicio);
        Fondo.repaint();
        Fondo.revalidate();
        
        hilo.start();

        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/Icono_200.png"));
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(LOGO.getWidth(), LOGO.getHeight(), Image.SCALE_DEFAULT));
        LOGO.setIcon(icono);
        this.repaint();

        ImageIcon imagen2 = new ImageIcon(getClass().getResource("/img/donar.png"));
        Icon icono2 = new ImageIcon(imagen2.getImage().getScaledInstance(donarimg.getWidth(), donarimg.getHeight(), Image.SCALE_DEFAULT));
        donarimg.setIcon(icono2);
        this.repaint();
        
        escaneopro.setVisible(false);
        estado.setAnimated(false);
        estado.setValue(100);
        estado.setString("");
        
        separador.setVisible(false);
            txt5.setVisible(false);
            S5.setVisible(false);
            txt6.setVisible(false);
            S6.setVisible(false);
            
            
    }


    
    public void cambios( boolean seb) {
        boolean cam=seb;
        CambiosDeVersion ver = new CambiosDeVersion(this, true);
        ver.setVisible(cam);
    }

    public void buscarUnidades() {
        DefaultTableModel listUni = (DefaultTableModel) listaUnidades.getModel();
        if (listaUnidades.getSelectedRowCount() > 0) {
            listaUnidades.removeRowSelectionInterval(listaUnidades.getSelectedRow(), listaUnidades.getSelectedRow());
        }
        unidades = File.listRoots();
        Object[] it = new Object[unidades.length];
        for (int i = 0; i < unidades.length; i++) {
            s1 = FileSystemView.getFileSystemView().getSystemDisplayName(unidades[i]);
            s2 = FileSystemView.getFileSystemView().getSystemTypeDescription(unidades[i]);
            if (FileSystemView.getFileSystemView().isFloppyDrive(unidades[i])) {
                s1 = "Unidad de Disquete (A:)";
            }
            if (s1.equalsIgnoreCase("")) {
                s1 = s2;
            }
            it[i] = s1;
        }
        while (listUni.getRowCount() != 0) {
            listUni.removeRow(0);
        }
        ruta = "";
        i = it;
        int s1 = i.length;
        for (int s2 = 0; s2 < s1; s2++) {
            Object it1 = i[s2];
            listUni.addRow(new Object[]{it1});
        }
        if (!S6.isOnOff()) {
            int lar = listUni.getRowCount()-1;
            for (int r = 0; r < lar; r++) {
                String valor = (String) listUni.getValueAt(r, 0);
                if (valor.equals("disco local") || valor.equals("local disk") || 
                        valor.equals("Disco local (C:)") || valor.equals("Windows (C:)")|| valor.equals("RECOVERY(D:)")|| valor.equals("Recovery(D:)")) {
                    listUni.removeRow(r);
                }
            }
        }
    }

    public void UnidadesVacunar() {
        DefaultTableModel listUni = (DefaultTableModel) listavacuna.getModel();
        if (listavacuna.getSelectedRowCount() > 0) {
            listavacuna.removeRowSelectionInterval(listavacuna.getSelectedRow(), listavacuna.getSelectedRow());
        }
        unidades = File.listRoots();
        Object[] it = new Object[unidades.length];
        for (int i = 0; i < unidades.length; i++) {
            s1 = FileSystemView.getFileSystemView().getSystemDisplayName(unidades[i]);
            s2 = FileSystemView.getFileSystemView().getSystemTypeDescription(unidades[i]);
            if (FileSystemView.getFileSystemView().isFloppyDrive(unidades[i])) {
                s1 = "Unidad de Disquete (A:)";
            }
            if (s1.equalsIgnoreCase("")) {
                s1 = s2;
            }
            it[i] = s1;
        }
        while (listUni.getRowCount() != 0) {
            listUni.removeRow(0);
        }
        univar.setText("TIPO DE UNIDAD");
        ruta = "";
        i = it;
        int s1 = i.length;
        for (int s2 = 0; s2 < s1; s2++) {
            Object it1 = i[s2];
            listUni.addRow(new Object[]{it1});
        }
        if (!S6.isOnOff()) {
            int lar = listUni.getRowCount()-1;
            for (int r = 0; r < lar; r++) {
                String valor = (String) listUni.getValueAt(r, 0);
                if (valor.equals("disco local") || valor.equals("local disk") || 
                        valor.equals("Disco local (C:)") || valor.equals("Windows (C:)")|| valor.equals("RECOVERY(D:)")|| valor.equals("Recovery(D:)")) {
                    listUni.removeRow(r);
                }
            }
        }
    }
    
    public void vac() {
        if (analizando) {
//            JOptionPane.showMessageDialog(null, "Se esta ejecutando un analisis.\nIntente al terminar", "Error", 0);
            Error er = new Error(this, true);
            er.setVisible(true);
        } else if (listavacuna.getSelectedRowCount() > 0) {
            uniSelec = (String) listavacuna.getValueAt(listavacuna.getSelectedRow(), 0);
            char[] aCaracteres = uniSelec.toCharArray();
            for (int x = 0; x < aCaracteres.length; x++) {
                if (String.valueOf(aCaracteres[x]).equals("(")) {
                    ruta = String.valueOf(aCaracteres[(x + 1)]);
                    abs22 = String.valueOf(ruta);
                    break;
                }
            }
            String a = (String) System.getenv("windir");
            String Win = String.valueOf(a.charAt(0)); 
            if(abs22.equals(Win)){//                String[] opciones = {"Si", "No"};
//                int opcion = JOptionPane.showOptionDialog(null, "Si la UNIDAD que seleccionaste es la UNIDAD\ndonde se encuentra el SISTEMA OPERATIVO\nse recomienda no continuar con el analisis.\n¿Deseas continuar?", "Advertencia", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, opciones, null);
                VarInfo = false;
                Info in = new Info(this, true);
                in.setVisible(true);

                if (VarInfo) {
                    Vacunar.VacunarUnidad(ruta, uniSelec, this);
                } else {
                    vacunabar.setAnimated(false);
                    vacunabar.setValue(0);
                    vacunabar.setString("");
                    ruta = "";
                }
            } else {
                Vacunar.VacunarUnidad(ruta, uniSelec, this);
            }
        } else {
            Error1 e = new Error1(this, true);
            e.setVisible(true);
//            JOptionPane.showMessageDialog(null, "Seleccione un dispositivo", "Error", 0);

        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        PrincipalVentana = new javax.swing.JPanel();
        Cabecera = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Botones = new javax.swing.JPanel();
        inicio = new principal.MaterialButton();
        desinfectar = new principal.MaterialButton();
        vacuna = new principal.MaterialButton();
        cuarentena = new principal.MaterialButton();
        acerca = new principal.MaterialButton();
        Fondo = new javax.swing.JPanel();
        Acerca = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jXHyperlink1 = new org.jdesktop.swingx.JXHyperlink();
        jLabel12 = new javax.swing.JLabel();
        actualizar = new principal.MaterialButton();
        barupdate = new ProgressBarX.RSProgressBarAnimated();
        jXHyperlink2 = new org.jdesktop.swingx.JXHyperlink();
        donarimg = new javax.swing.JLabel();
        Ajustes = new javax.swing.JPanel();
        txt1 = new javax.swing.JLabel();
        S1 = new principal.Switch();
        S2 = new principal.Switch();
        S4 = new principal.Switch();
        separador = new javax.swing.JSeparator();
        txt4 = new javax.swing.JLabel();
        S5 = new principal.Switch();
        S6 = new principal.Switch();
        txt5 = new javax.swing.JLabel();
        txt6 = new javax.swing.JLabel();
        txt2 = new javax.swing.JLabel();
        txt3 = new javax.swing.JLabel();
        Instalar = new javax.swing.JButton();
        Vacuna = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listavacuna = new javax.swing.JTable();
        basico = new check.CheckBlue();
        medio = new check.CheckBlue();
        total = new check.CheckBlue();
        materialButton1 = new principal.MaterialButton();
        univar = new javax.swing.JLabel();
        vacunabar = new ProgressBarX.RSProgressBarAnimated();
        jLabel13 = new javax.swing.JLabel();
        icounidad = new javax.swing.JLabel();
        Desinfectar = new javax.swing.JPanel();
        btnAnalizar = new javax.swing.JLabel();
        estado = new ProgressBarX.RSProgressCircleAnimated();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaUnidades = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        escaneopro = new check.CheckBlue();
        ligaescaner = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        Inicio = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        LOGO = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);

        PrincipalVentana.setBackground(new java.awt.Color(255, 255, 255));
        PrincipalVentana.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Cabecera.setBackground(new java.awt.Color(52, 73, 94));
        Cabecera.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_92.png"))); // NOI18N
        Cabecera.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 100));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 27)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("USB Shield");
        Cabecera.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 37, -1, -1));

        jPanel4.setBackground(new java.awt.Color(52, 73, 94));
        jPanel4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel4MouseDragged(evt);
            }
        });
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel4MousePressed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/boton-x.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/boton-mimimizar.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(526, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                .addContainerGap())
        );

        Cabecera.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 40));

        PrincipalVentana.add(Cabecera, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, 100));

        Botones.setBackground(new java.awt.Color(52, 73, 94));

        inicio.setBackground(new java.awt.Color(0, 153, 153));
        inicio.setForeground(new java.awt.Color(255, 255, 255));
        inicio.setText("Inicio");
        inicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inicioActionPerformed(evt);
            }
        });

        desinfectar.setBackground(new java.awt.Color(0, 153, 153));
        desinfectar.setForeground(new java.awt.Color(255, 255, 255));
        desinfectar.setText("Desinfectar");
        desinfectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desinfectarActionPerformed(evt);
            }
        });

        vacuna.setBackground(new java.awt.Color(0, 153, 153));
        vacuna.setForeground(new java.awt.Color(255, 255, 255));
        vacuna.setText("Vacunar");
        vacuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vacunaActionPerformed(evt);
            }
        });

        cuarentena.setBackground(new java.awt.Color(0, 153, 153));
        cuarentena.setForeground(new java.awt.Color(255, 255, 255));
        cuarentena.setText("Ajustes");
        cuarentena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuarentenaActionPerformed(evt);
            }
        });

        acerca.setBackground(new java.awt.Color(0, 153, 153));
        acerca.setForeground(new java.awt.Color(255, 255, 255));
        acerca.setText("Acerca");
        acerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BotonesLayout = new javax.swing.GroupLayout(Botones);
        Botones.setLayout(BotonesLayout);
        BotonesLayout.setHorizontalGroup(
            BotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(desinfectar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vacuna, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cuarentena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(acerca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        BotonesLayout.setVerticalGroup(
            BotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BotonesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(desinfectar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vacuna, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cuarentena, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(acerca, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        PrincipalVentana.add(Botones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 130, 310));

        Fondo.setBackground(new java.awt.Color(255, 255, 255));
        Fondo.setLayout(new java.awt.CardLayout());

        Acerca.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("SOFTWARE DESARROLLADO POR");

        jXHyperlink1.setText("YONATHAN ROMAN SALGADO");
        jXHyperlink1.setToolTipText("Facebook");
        jXHyperlink1.setFocusable(false);
        jXHyperlink1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jXHyperlink1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jXHyperlink1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXHyperlink1ActionPerformed(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/acerca.png"))); // NOI18N

        actualizar.setBackground(new java.awt.Color(0, 153, 153));
        actualizar.setForeground(new java.awt.Color(255, 255, 255));
        actualizar.setText("ACTUALIZAR SOFTWARE");
        actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarActionPerformed(evt);
            }
        });

        barupdate.setBorder(null);
        barupdate.setToolTipText("");
        barupdate.setValue(0);
        barupdate.setAnimated(false);
        barupdate.setString("Beta 1.2");

        jXHyperlink2.setText("Creditos");
        jXHyperlink2.setClickedColor(new java.awt.Color(0, 51, 255));
        jXHyperlink2.setContentAreaFilled(false);
        jXHyperlink2.setFocusable(false);
        jXHyperlink2.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jXHyperlink2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jXHyperlink2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXHyperlink2ActionPerformed(evt);
            }
        });

        donarimg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        donarimg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                donarimgMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout AcercaLayout = new javax.swing.GroupLayout(Acerca);
        Acerca.setLayout(AcercaLayout);
        AcercaLayout.setHorizontalGroup(
            AcercaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AcercaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AcercaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AcercaLayout.createSequentialGroup()
                        .addGap(0, 157, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addGap(165, 165, 165))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AcercaLayout.createSequentialGroup()
                        .addGroup(AcercaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(barupdate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jXHyperlink1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(AcercaLayout.createSequentialGroup()
                .addGroup(AcercaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AcercaLayout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(donarimg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(AcercaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jXHyperlink2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        AcercaLayout.setVerticalGroup(
            AcercaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AcercaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXHyperlink1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jXHyperlink2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(AcercaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(AcercaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AcercaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(donarimg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(barupdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Fondo.add(Acerca, "card2");

        Ajustes.setBackground(new java.awt.Color(255, 255, 255));
        Ajustes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txt1.setText("Escanear virus poco usuales");
        Ajustes.add(txt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 313, 30));

        S1.setBackgroundColor(java.awt.Color.blue);
        S1.setOnOff(false);
        S1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                S1MouseClicked(evt);
            }
        });
        Ajustes.add(S1, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 11, 50, 30));

        S2.setBackgroundColor(java.awt.Color.blue);
        S2.setOnOff(false);
        Ajustes.add(S2, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 52, 50, 30));

        S4.setBackgroundColor(java.awt.Color.blue);
        S4.setOnOff(false);
        S4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                S4MouseClicked(evt);
            }
        });
        Ajustes.add(S4, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 129, 50, 30));
        Ajustes.add(separador, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 172, 440, 10));

        txt4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txt4.setText("Ajustes avanzados ¡Sólo debe activar si sabe lo que hace! ");
        Ajustes.add(txt4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 129, 371, 32));

        S5.setBackgroundColor(java.awt.Color.blue);
        S5.setOnOff(false);
        S5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                S5MouseClicked(evt);
            }
        });
        Ajustes.add(S5, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 200, 50, 30));

        S6.setBackgroundColor(java.awt.Color.blue);
        S6.setOnOff(false);
        S6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                S6MouseClicked(evt);
            }
        });
        Ajustes.add(S6, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 241, 50, 30));

        txt5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txt5.setText("Caracteristícas experimentales");
        Ajustes.add(txt5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 310, 30));

        txt6.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txt6.setText("Mostrar todos las unidades de almacenamiento");
        Ajustes.add(txt6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 241, 310, 30));

        txt2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txt2.setText("Mostrar tiempo de analisis ");
        Ajustes.add(txt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 313, 30));

        txt3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txt3.setText("Instalar la version lite de USB Shield en su USB");
        Ajustes.add(txt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 93, 313, 30));

        Instalar.setText("Instalar");
        Instalar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InstalarActionPerformed(evt);
            }
        });
        Ajustes.add(Instalar, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, -1, 30));

        Fondo.add(Ajustes, "card2");

        Vacuna.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Selecciona la unidad a vacunar y la vacuna:");

        listavacuna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SELECCIONAR UNIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listavacuna.setRowHeight(22);
        listavacuna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listavacunaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listavacuna);

        buttonGroup1.add(basico);
        basico.setText("BASICO");
        basico.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        buttonGroup1.add(medio);
        medio.setText("MEDIO");
        medio.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        buttonGroup1.add(total);
        total.setText("TOTAL");
        total.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        materialButton1.setBackground(new java.awt.Color(0, 153, 153));
        materialButton1.setForeground(new java.awt.Color(255, 255, 255));
        materialButton1.setText("VACUNAR");
        materialButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materialButton1ActionPerformed(evt);
            }
        });

        univar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N

        vacunabar.setBorder(null);
        vacunabar.setValue(0);
        vacunabar.setAnimated(false);
        vacunabar.setString("");

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText("Seleccione nivel de la vacuna:");

        icounidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout VacunaLayout = new javax.swing.GroupLayout(Vacuna);
        Vacuna.setLayout(VacunaLayout);
        VacunaLayout.setHorizontalGroup(
            VacunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VacunaLayout.createSequentialGroup()
                .addGroup(VacunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(VacunaLayout.createSequentialGroup()
                        .addGroup(VacunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(VacunaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(VacunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addGroup(VacunaLayout.createSequentialGroup()
                                        .addComponent(basico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(medio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(VacunaLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel13))
                            .addGroup(VacunaLayout.createSequentialGroup()
                                .addGap(104, 104, 104)
                                .addComponent(materialButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 35, Short.MAX_VALUE))
                    .addGroup(VacunaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(VacunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vacunabar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(VacunaLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(icounidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(univar, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        VacunaLayout.setVerticalGroup(
            VacunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VacunaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(VacunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(VacunaLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(VacunaLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(VacunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(icounidad, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, VacunaLayout.createSequentialGroup()
                                .addComponent(univar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)))
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(VacunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(basico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(medio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(vacunabar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(materialButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))))
        );

        Fondo.add(Vacuna, "card2");

        Desinfectar.setBackground(new java.awt.Color(255, 255, 255));
        Desinfectar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAnalizar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAnalizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAnalizar.setText("Analizar");
        btnAnalizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnalizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnalizarMouseClicked(evt);
            }
        });
        Desinfectar.add(btnAnalizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 130, 110, 20));

        estado.setForeground(java.awt.Color.blue);
        estado.setAnimated(false);
        estado.setString("");
        Desinfectar.add(estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 210, 230));

        listaUnidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SELECCIONAR UNIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listaUnidades.setRowHeight(22);
        listaUnidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaUnidadesMouseClicked(evt);
            }
        });
        listaUnidades.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                listaUnidadesPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(listaUnidades);

        Desinfectar.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 130, 190));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("Selecciona una unidad a desinfectar:");
        Desinfectar.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        escaneopro.setText("Activar el escaneo profundo");
        escaneopro.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        Desinfectar.add(escaneopro, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 210, 200, -1));
        Desinfectar.add(ligaescaner, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 440, 20));
        Desinfectar.add(time, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 250, 180, 20));

        Fondo.add(Desinfectar, "card2");

        Inicio.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("USB Shield");

        LOGO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LOGO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Icono_200.png"))); // NOI18N
        LOGO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LOGOMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LOGOMouseExited(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Desinfección y vacunación de sus unidades usb");

        javax.swing.GroupLayout InicioLayout = new javax.swing.GroupLayout(Inicio);
        Inicio.setLayout(InicioLayout);
        InicioLayout.setHorizontalGroup(
            InicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InicioLayout.createSequentialGroup()
                .addGroup(InicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InicioLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(LOGO)
                        .addGap(0, 124, Short.MAX_VALUE))
                    .addGroup(InicioLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(InicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        InicioLayout.setVerticalGroup(
            InicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LOGO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addContainerGap())
        );

        Fondo.add(Inicio, "card2");

        PrincipalVentana.add(Fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 109, 460, 300));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PrincipalVentana, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PrincipalVentana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inicioActionPerformed
        Fondo.removeAll();
        Fondo.repaint();
        Fondo.revalidate();

        Fondo.add(Inicio);
        Fondo.repaint();
        Fondo.revalidate();
    }//GEN-LAST:event_inicioActionPerformed

    private void desinfectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desinfectarActionPerformed
        Fondo.removeAll();
        Fondo.repaint();
        Fondo.revalidate();
        Antivirus.estado.setForeground(Color.blue);
        btnAnalizar.setText("Analizar");
        Fondo.add(Desinfectar);
        Fondo.repaint();
        Fondo.revalidate();
    }//GEN-LAST:event_desinfectarActionPerformed

    private void vacunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vacunaActionPerformed
        Fondo.removeAll();
        Fondo.repaint();
        Fondo.revalidate();

        basico.setSelected(false);
        medio.setSelected(false);
        total.setSelected(false);
        
        Fondo.add(Vacuna);
        Fondo.repaint();
        Fondo.revalidate();
    }//GEN-LAST:event_vacunaActionPerformed

    private void cuarentenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuarentenaActionPerformed
        Fondo.removeAll();
        Fondo.repaint();
        Fondo.revalidate();

        Fondo.add(Ajustes);
        Fondo.repaint();
        Fondo.revalidate();
    }//GEN-LAST:event_cuarentenaActionPerformed

    private void acercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercaActionPerformed
        Fondo.removeAll();
        Fondo.repaint();
        Fondo.revalidate();

        Fondo.add(Acerca);
        Fondo.repaint();
        Fondo.revalidate();
    }//GEN-LAST:event_acercaActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jPanel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jPanel4MousePressed

    private void jPanel4MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseDragged
        Point mueve = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(mueve.x - x, mueve.y - y);
    }//GEN-LAST:event_jPanel4MouseDragged

    private void btnAnalizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnalizarMouseClicked
        Antivirus.estado.setForeground(Color.blue);
        if (analizando) {
//            JOptionPane.showMessageDialog(null, "Se esta ejecutando un analisis.\nIntente al terminar", "Error", 0);
            Error er = new Error(this, true);
            er.setVisible(true);
        } else if (listaUnidades.getSelectedRowCount() > 0) {
            uniSelec = (String) listaUnidades.getValueAt(listaUnidades.getSelectedRow(), 0);
            char[] aCaracteres = uniSelec.toCharArray();
            for (int x = 0; x < aCaracteres.length; x++) {
                if (String.valueOf(aCaracteres[x]).equals("(")) {
                    ruta = String.valueOf(aCaracteres[(x + 1)]);
                    abs22 = String.valueOf(ruta);
                    break;
                    
                }
            }
            String a = (String) System.getenv("windir");
            String Win = String.valueOf(a.charAt(0)); 
            if(abs22.equals(Win)){
//                String[] opciones = {"Si", "No"};
//                int opcion = JOptionPane.showOptionDialog(null, "Si la UNIDAD que seleccionaste es la UNIDAD\ndonde se encuentra el SISTEMA OPERATIVO\nse recomienda no continuar con el analisis.\n¿Deseas continuar?", "Advertencia", JOptionPane.DEFAULT_OPTION // Tipo de opciones
//                        ,JOptionPane.INFORMATION_MESSAGE, null, opciones, null);
                VarInfo2 = false;
                Info2 in2 = new Info2(this, true);
                in2.setVisible(true);
                //Analisis de disco C:
                if (VarInfo2) {
                    System.out.println("Ruta2: "+ruta);
                    Reparar.AnalizaUnidad(ruta, uniSelec, this);
                } else {
                    estado.setAnimated(false);
                    estado.setValue(100);
                    estado.setString("");
                    ruta = "";
                }
            } else {
                //Analisis de uniddes
                Reparar.AnalizaUnidad(ruta, uniSelec, this);
            }
        } else {
//            JOptionPane.showMessageDialog(null, "Seleccione un dispositivo", "Error", 0);
            Error1 error = new Error1(this, true);
            error.setVisible(true);
        }
    }//GEN-LAST:event_btnAnalizarMouseClicked

    private void listaUnidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaUnidadesMouseClicked
        if (analizando) {
//            JOptionPane.showMessageDialog(null, "Se esta ejecutando un analisis.\nIntente al terminar", "Error", 0);
            Error er = new Error(this, true);
            er.setVisible(true);
        } else {
            Antivirus.estado.setForeground(Color.blue);
            btnAnalizar.setText("Analizar");
//            int fila = listaUnidades.getSelectedRow();
//            try {
//                    long lo = Antivirus.unidades[fila].getFreeSpace();
//                    time.setText("Espacio en USB: "+lo);
//                } catch (ArrayIndexOutOfBoundsException | NullPointerException localArrayIndexOutOfBoundsException) {
//                }
        }
    }//GEN-LAST:event_listaUnidadesMouseClicked

    private void listaUnidadesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_listaUnidadesPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_listaUnidadesPropertyChange

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jXHyperlink1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXHyperlink1ActionPerformed
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI("https://www.facebook.com/yonathaanR"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jXHyperlink1ActionPerformed

    private void actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarActionPerformed
        barupdate.setAnimated(true);
        barupdate.setString("Buscando...");
//        JOptionPane.showMessageDialog(null, "Esta opcion se agregara mas adelante...", "Actualizar  Alpha 2.0", 1);
        Update up = new Update(this, true);
        up.setVisible(true);
        barupdate.setAnimated(false);
        barupdate.setValue(0);
        barupdate.setString("Version: Beta 1.2");
    }//GEN-LAST:event_actualizarActionPerformed

    private void listavacunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listavacunaMouseClicked
        String a;
        if (listavacuna.getSelectedRow() != -1) {
            int fila = listavacuna.getSelectedRow()+1;
            try {
                a = FileSystemView.getFileSystemView().getSystemDisplayName(Antivirus.unidades[fila]);
                univar.setText(a);
                ImageIcon iconoSys = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(unidades[fila]);
                ImageIcon icono = new ImageIcon(iconoSys.getImage().getScaledInstance(icounidad.getWidth(), icounidad.getHeight(), 1));
                icounidad.setIcon(icono);
                icounidad.setIcon(icono);
                icounidad.setIcon(icono);
            } catch (ArrayIndexOutOfBoundsException | NullPointerException localArrayIndexOutOfBoundsException) {
            }
        }
    }//GEN-LAST:event_listavacunaMouseClicked

    private void materialButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materialButton1ActionPerformed
        if (basico.isSelected()) {
            vac();
        } else {
            if (medio.isSelected()) {
                vac();
            } else {
                if (total.isSelected()) {
                    vac();
                } else {
//            JOptionPane.showMessageDialog(null, "Selecciona un nivel de vacuna", "Error", 0);
                    ErrorVacuna ev = new ErrorVacuna(this, true);
                    ev.setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_materialButton1ActionPerformed

    private void S4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_S4MouseClicked
        
        if(S4.isOnOff()){
            //Prendido
            separador.setVisible(true);
            txt5.setVisible(true);
            S5.setVisible(true);
            txt6.setVisible(true);
            S6.setVisible(true);
        }else{
            //Apagado
            separador.setVisible(false);
            txt5.setVisible(false);
            S5.setVisible(false);
            txt6.setVisible(false);
            S6.setVisible(false);
            S5.setOnOff(false);
            S6.setOnOff(false);
            escaneopro.setVisible(false);
            buscarUnidades();
            UnidadesVacunar();
        }
        
    }//GEN-LAST:event_S4MouseClicked

    private void S5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_S5MouseClicked
        if (S5.isOnOff()) {
            String[] opciones = {"Si", "No"};
            String[] adver = {"Desactivar", "Seguir"};
            int opcion = JOptionPane.showOptionDialog(null, "Es recomendable no utilizar esta caracteristica \n en unidades con el numero de archivos mayores a 200 \n ya que puede ocacionar problemas en el computador\n¿Deseas continuar?", "¡Advertencia!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, opciones, null);
            if (opciones[opcion] == "Si") {
                int opcion1 = JOptionPane.showOptionDialog(null, "Le sugerimos no usar esta caracteristica\n¿Deseas continuar?", "¡Advertencia!", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, adver, null);
                if (adver[opcion1] == "Seguir") {
                    escaneopro.setVisible(true);
                } else {
                    escaneopro.setVisible(false);
                    Fondo.removeAll();
                    Fondo.repaint();
                    Fondo.revalidate();

                    Fondo.add(Ajustes);
                    Fondo.repaint();
                    Fondo.revalidate();
                    S5.setOnOff(false);
                }
            } else {
                escaneopro.setVisible(false);
                Fondo.removeAll();
                Fondo.repaint();
                Fondo.revalidate();

                Fondo.add(Ajustes);
                Fondo.repaint();
                Fondo.revalidate();
                S5.setOnOff(false);

            }
        } else {
            Fondo.removeAll();
            Fondo.repaint();
            Fondo.revalidate();

            Fondo.add(Ajustes);
            Fondo.repaint();
            Fondo.revalidate();
            escaneopro.setVisible(false);
        }
    }//GEN-LAST:event_S5MouseClicked

    private void InstalarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InstalarActionPerformed
        JOptionPane.showMessageDialog(null, "Esta opcion se agregara mas adelante...", "Proximamente", 1);
//        Instalar.setText("Desinstalar");
//        InstalLite.Setup a = new InstalLite.Setup();
//        a.setVisible(true);
    }//GEN-LAST:event_InstalarActionPerformed

    private void S1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_S1MouseClicked
        if (S1.isOnOff()) {
            Fondo.removeAll();
            Fondo.repaint();
            Fondo.revalidate();
            Fondo.add(Ajustes);
            Fondo.repaint();
            Fondo.revalidate();
        }else{
            Fondo.removeAll();
            Fondo.repaint();
            Fondo.revalidate();
            Fondo.add(Ajustes);
            Fondo.repaint();
            Fondo.revalidate();
        }
    }//GEN-LAST:event_S1MouseClicked

    private void S6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_S6MouseClicked
        UnidadesVacunar();
        buscarUnidades();
    }//GEN-LAST:event_S6MouseClicked

    private void LOGOMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LOGOMouseEntered
        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/escudo2.png"));
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(LOGO.getWidth(), LOGO.getHeight(), Image.SCALE_DEFAULT));
        LOGO.setIcon(icono);
        this.repaint();
    }//GEN-LAST:event_LOGOMouseEntered

    private void LOGOMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LOGOMouseExited
        ImageIcon imagen = new ImageIcon(getClass().getResource("/img/Icono_200.png"));
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(LOGO.getWidth(), LOGO.getHeight(), Image.SCALE_DEFAULT));
        LOGO.setIcon(icono);
        this.repaint();
    }//GEN-LAST:event_LOGOMouseExited

    private void donarimgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_donarimgMouseClicked
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI("https://www.paypal.me/yonathanr11/10"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_donarimgMouseClicked

    private void jXHyperlink2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXHyperlink2ActionPerformed
        creditos in = new creditos(this, true);
                in.setVisible(true);
    }//GEN-LAST:event_jXHyperlink2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Antivirus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Antivirus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Antivirus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Antivirus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Antivirus.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Antivirus.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Antivirus.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Antivirus.class.getName()).log(Level.SEVERE, null, ex);
                }
                new Antivirus().setVisible(true);
            }
        });
    }

    public void run() {
        letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "I", "Z"};

        drives = new File[this.letters.length];
        isDrive = new boolean[this.letters.length];
        for (int i = 0; i < letters.length; i++) {
            drives[i] = new File(letters[i] + ":/");

            isDrive[i] = drives[i].canRead();
        }
        for (;;) {
            for (int i = 0; i < letters.length; i++) {
                boolean pluggedIn = drives[i].canRead();
                if (pluggedIn != isDrive[i]) {
                    if (pluggedIn) {
                        buscarUnidades();
                        UnidadesVacunar();
                    } else {
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Antivirus.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        buscarUnidades();
                        UnidadesVacunar();
                    }
                    isDrive[i] = pluggedIn;
                }
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException localInterruptedException1) {
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Acerca;
    private javax.swing.JPanel Ajustes;
    private javax.swing.JPanel Botones;
    private javax.swing.JPanel Cabecera;
    private javax.swing.JPanel Desinfectar;
    private javax.swing.JPanel Fondo;
    private javax.swing.JPanel Inicio;
    private javax.swing.JButton Instalar;
    private javax.swing.JLabel LOGO;
    private javax.swing.JPanel PrincipalVentana;
    public static principal.Switch S1;
    public static principal.Switch S2;
    private principal.Switch S4;
    private principal.Switch S5;
    private principal.Switch S6;
    private javax.swing.JPanel Vacuna;
    private principal.MaterialButton acerca;
    private principal.MaterialButton actualizar;
    private ProgressBarX.RSProgressBarAnimated barupdate;
    public static check.CheckBlue basico;
    public static javax.swing.JLabel btnAnalizar;
    private javax.swing.ButtonGroup buttonGroup1;
    private principal.MaterialButton cuarentena;
    private principal.MaterialButton desinfectar;
    private javax.swing.JLabel donarimg;
    public static check.CheckBlue escaneopro;
    public static ProgressBarX.RSProgressCircleAnimated estado;
    private javax.swing.JLabel icounidad;
    private principal.MaterialButton inicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXHyperlink jXHyperlink1;
    private org.jdesktop.swingx.JXHyperlink jXHyperlink2;
    public static javax.swing.JLabel ligaescaner;
    private javax.swing.JTable listaUnidades;
    private javax.swing.JTable listavacuna;
    private principal.MaterialButton materialButton1;
    public static check.CheckBlue medio;
    private javax.swing.JSeparator separador;
    public static javax.swing.JLabel time;
    public static check.CheckBlue total;
    private javax.swing.JLabel txt1;
    private javax.swing.JLabel txt2;
    private javax.swing.JLabel txt3;
    private javax.swing.JLabel txt4;
    private javax.swing.JLabel txt5;
    private javax.swing.JLabel txt6;
    private javax.swing.JLabel univar;
    private principal.MaterialButton vacuna;
    public static ProgressBarX.RSProgressBarAnimated vacunabar;
    // End of variables declaration//GEN-END:variables
}

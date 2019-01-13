package Antivirus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * @author Yonathan
 */
public class Vacunar {

    public static void VacunarUnidad(String ruta, String uniSelec, JFrame ventana) {
//        JOptionPane.showMessageDialog(null, "Clase VacunarUnidad", "Info", 1);

        try {
            Process p = Runtime.getRuntime().exec("cmd /c if exist " + ruta + ": echo existe");
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String existeDisp = br.readLine();
            if (existeDisp != null) {
                String[] opciones = {"Si", "No"};
                int opcion = JOptionPane.showOptionDialog(null, "Se creara un archivo 'autorun' en " + uniSelec + " para vacunarla\n¿Deseas continuar?", "Advertencia", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, null);
                if (opciones[opcion] == "Si") {
                    new Thread(new Runnable() {
                        public void run() {
                            Reparar.totalArchivos(new File(ruta + ":\\"));
                            Antivirus.vacunabar.setAnimated(true);
                            Antivirus.vacunabar.setString("Vacunando...");
                            Antivirus.analizando = true;
                            try {
                                try {
                                    Process a = Runtime.getRuntime().exec("attrib /S /D -R -A -S -H " + ruta + ":\\*.*");
                                    InputStream is = a.getInputStream();
                                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                                    String existeDisp = br.readLine();
                                    boolean no_exit = true;
                                    while (no_exit) {
                                        try {
                                            a.exitValue();
                                            no_exit = false;
                                        } catch (IllegalThreadStateException exception) {
                                            no_exit = true;
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//----------------------------------------------------------------------
                                if (Antivirus.basico.isSelected()) {
                                    //autorun.inf , Recycler y  Autorun.ini
                                    VacunaBasico(ruta);
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\autorun.inf");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Recycler");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Autorun.ini");
                                    Thread.sleep(1500);
                                }
                                if (Antivirus.medio.isSelected()) {
                                    //Bin , Driver , MSOCache , Recycled
                                    VacunaMedio(ruta);
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\autorun.inf");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Recycler");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Autorun.ini");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Bin");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Driver");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\MSOCache");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Recycled");
                                    Thread.sleep(2500);
                                }
                                if (Antivirus.total.isSelected()) {
                                    // Restore , CARPETA autorun.inf
                                    VacunaTotal(ruta);
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\autorun.inf");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Recycler");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Autorun.ini");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Bin");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Driver");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\MSOCache");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Recycled");
                                    Runtime.getRuntime().exec("attrib +h +r +s " + ruta + ":\\Restore");
                                    Thread.sleep(3000);
                                }
//----------------------------------------------------------------------   
                                Antivirus.analizando = false;
                                Antivirus.vacunabar.setAnimated(false);
                                Antivirus.vacunabar.setValue(100);
                                Antivirus.vacunabar.setString("Finalizado");
                                Antivirus.analizando = false;
                            } catch (Exception localException2) {
                            }
                        }
                    })
                            .start();
                } else {
                    Antivirus.vacunabar.setAnimated(false);
                    Antivirus.vacunabar.setValue(0);
                    Antivirus.vacunabar.setString("");
                    Antivirus.ruta = "";
                }
            } else {
                Antivirus.vacunabar.setAnimated(false);
                Antivirus.vacunabar.setValue(100);
                Antivirus.vacunabar.setString("");
                JOptionPane.showMessageDialog(null, "Error del Antivirus", "Error Critico", 0);
                Antivirus.ruta = "";
            }
        } catch (Exception a) {
            a.printStackTrace();
        }

    }

    public static void VacunaBasico(String ruta) throws IOException {
        //autorun.inf , Recycler y  Autorun.ini
        File archivo = new File(ruta + ":/autorun.inf");
        File archivo1 = new File(ruta + ":/Recycler");
        File archivo2 = new File(ruta + ":/Autorun.ini");
        BufferedWriter bw;
        BufferedWriter bw1;
        BufferedWriter bw2;
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("Protegemos su dispositivo - USB Sheld Vacuna v2.0");
            bw1 = new BufferedWriter(new FileWriter(archivo1));
            bw1.write("Protegemos su dispositivo - USB Sheld Vacuna v2.0");
            bw2 = new BufferedWriter(new FileWriter(archivo2));
            bw2.write("Protegemos su dispositivo - USB Sheld Vacuna v2.0");
        bw.close();
        bw1.close();
        bw2.close();
    }

        public static void VacunaMedio(String ruta) throws IOException {
        //Bin , Driver , MSOCache , Recycled
        VacunaBasico(ruta);
        File archivo = new File(ruta + ":/Bin");
        File archivo1 = new File(ruta + ":/Driver");
        File archivo2 = new File(ruta + ":/MSOCache");
        File archivo3 = new File(ruta + ":/Recycled");
        BufferedWriter bw;
        BufferedWriter bw1;
        BufferedWriter bw2;
        BufferedWriter bw3;
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("Protegemos su dispositivo - USB Sheld Vacuna v2.0");
            bw1 = new BufferedWriter(new FileWriter(archivo1));
            bw1.write("Protegemos su dispositivo - USB Sheld Vacuna v2.0");
            bw2 = new BufferedWriter(new FileWriter(archivo2));
            bw2.write("Protegemos su dispositivo - USB Sheld Vacuna v2.0");
            bw3 = new BufferedWriter(new FileWriter(archivo3));
            bw3.write("Protegemos su dispositivo - USB Sheld Vacuna v2.0");
        bw.close();
        bw1.close();
        bw2.close();
        bw3.close();
    }
    
        
        public static void VacunaTotal(String ruta) throws IOException {
        // Restore , System Volume Information
        VacunaMedio(ruta);
        File archivo = new File(ruta + ":/Restore");
        BufferedWriter bw;
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("Protegemos su dispositivo - USB Sheld Vacuna v2.0");
        bw.close();
    }
}

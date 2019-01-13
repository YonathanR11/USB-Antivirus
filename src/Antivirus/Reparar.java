package Antivirus;

import static Antivirus.Antivirus.time;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

    

public class Reparar {
    static boolean VarTxT;
static long start=-1,stop=-1;
    
   public static void AnalizaUnidad(String ruta, String uniSelec, JFrame ventana) {
        try {
            Process p = Runtime.getRuntime().exec("cmd /c if exist " + ruta + ": echo existe");
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String existeDisp = br.readLine();
            if (existeDisp != null) {
                String[] opciones = {"Si", "No"};
                int opcion = JOptionPane.showOptionDialog(null, "Se analizara la unidad " + uniSelec + "\n¿Deseas continuar?", "Advertencia", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, null);
                if (opciones[opcion] == "Si") {
                    StartTime();
                    new Thread(new Runnable() {
                        public void run() {
//                            new rojerusan.RSNotifyAnimated("USB Shield", "Analisis iniciado.", 10, RSNotifyAnimated.PositionNotify.BottomRight, RSNotifyAnimated.AnimationNotify.RightLeft, RSNotifyAnimated.TypeNotify.INFORMATION).setVisible(true);
                            Reparar.totalArchivos(new File(ruta + ":\\"));
                            Antivirus.estado.setAnimated(true);
                            Antivirus.btnAnalizar.setText("Analizando...");
                            Antivirus.analizando = true;
                            try {
                                try {
                                    Process a = Runtime.getRuntime().exec("attrib /S /D -R -A -S -H " + ruta + ":\\*.*");
                                    InputStream is = a.getInputStream();
                                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                                    String existeDisp = br.readLine();
//                                    System.out.println(existeDisp);
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
                                if (Antivirus.S1.isOnOff()) {
                                    String[] archivo = {"Deegu", "autorun", "*._*", "*.-*", "Restore", "Recycler", "Recycled", "MSOCache", "Driver", "Bin", "*.acbem", "*.iv", "._.Trashes", "Virus.EXE", "*.vbs", "*.scr", "Virus.mpl", "*.cmd", "*.inf.ren", "Virus.exe", "*.com", "*.cmd", "*.inf", "*.lnk", "Viruss.EXE", "Virus.gen", "autorun.inf", "autorun.inf.ren", "autoexec.bat", "autoexec.bat", "*.sys", "*.db", "*.init", "*.ini", "*.001", "*.xxl", "autoexec.bat", "wgatray.exe", "wgatray.exe", "WMIPRVSE.exe", "ASWREGSVR64.exe", "WIAACMGR.EXE", "AUTORUN.INF.exe", "UPDATE.exe", "FREEHAND 10.exe", "ICOLORFOLDER.EXE", "2XPIZESETTINGS.exe", "*.gen", "Kit/Terbits.A.1", "VTTRAYP.exe", "RESTORPRICE.exe", "VCS6CORE.exe", "DEFENDER.exe", "porn.exe", "x.mpg set", "x.mpeg", "ert.dll", "roimeex.exe", "roimeex.scr", "roimeexx.exe", "x.exe", "ROIMEEX.src", "buaogi.exe", "attrib.exe", "sexy.exe", "Passwords.exe", "WSCNTFY.exe", "ALG.exe", "isuninst.exe", "kmservice.exe", "rundll32.exe", "autorun.exe", "ultraiso.exe", "cauuyex.exe", "cauuye.exe", "VFP6.exe", "DEVENV.exe", "WIAACMGR.exe", "WSCNTFY.exe", "APEMREG.exe", "RUNDLL32.EXE", "ENCSET.exe", "W32/virut.Gen", "txluh.exe", "poidu.scr", "x.exe", "spoolsv.exe", "ert.dll", "EXPLORER.exe", "IEXPLORE.exe", "FABS.exe", "Database.mpl", "NVSVC32.exe", "SPOOLSV.exe", "TRAYSERVER.exe", "USERINIT.exe", "VERCLSID.exe", "WORPAD.exe", "txluhx.exe", "hoaveo.exe", "hoaveox.exe", "xaosud.exe", "xaosudx.exe", "xaiguaf.exe", "xaiguafx.exe", "bbfbk3.exe", "faisal.*", "voeowo.exe", "SDCOT.exe", "jieku.exe", "jiekux.exe", "ert.dll", "x.exe", "Tmp001.{645FF040-5081-101B-9F08-00AA002F954E}", "voeowox.exe", "foialor.exe", "voipuis.exe", "voipuisx.exe", "foialorx.exe", "tumuv.exe", "tumuvx.exe", "commit.exe", "buoufo.exe", "buoufox.exe", "gusano.bat", "dbpiep.exe"};
                                    if (Antivirus.escaneopro.isSelected()) {
                                        for (int i = 0; i < archivo.length; i++) {
                                            String rutaArchivo = ruta + ":\\" + archivo[i];
                                            Runtime.getRuntime().exec("cmd /c del /f " + rutaArchivo);
                                        }
                                        AnalisisProfundo(new File(ruta + ":"));
                                    } else {
                                        for (int i = 0; i < archivo.length; i++) {
                                            String rutaArchivo = ruta + ":\\" + archivo[i];
                                            Runtime.getRuntime().exec("cmd /c del /f " + rutaArchivo);
                                        }
                                    }
                                } else {
                                    String[] archivo = {"Deegu", "autorun", "*._*", "*.-*", "Restore", "Recycler", "Recycled", "MSOCache", "Driver", "Bin", "*.acbem", "*.iv", "._.Trashes", "Virus.EXE", "*.vbs", "*.scr", "Virus.mpl", "*.cmd", "*.inf.ren", "Virus.exe", "*.com", "*.cmd", "*.inf", "*.lnk", "Viruss.EXE", "Virus.gen", "autorun.inf", "autorun.inf.ren", "autoexec.bat", "autoexec.bat", "*.sys", "*.db", "*.init", "*.ini", "*.001", "*.xxl", "autoexec.bat", "*.gen"};
                                    if (Antivirus.escaneopro.isSelected()) {
                                        for (int i = 0; i < archivo.length; i++) {
                                            String rutaArchivo = ruta + ":\\" + archivo[i];
                                            Runtime.getRuntime().exec("cmd /c del /f " + rutaArchivo);
                                        }
                                        AnalisisProfundo(new File(ruta + ":"));
                                    } else {
                                        for (int i = 0; i < archivo.length; i++) {
                                            String rutaArchivo = ruta + ":\\" + archivo[i];
                                            Runtime.getRuntime().exec("cmd /c del /f " + rutaArchivo);
                                        }
                                    }
                                }
                                
                                String nombre = "System volume Information";
                                String nameInicial = ruta + ":\\" + nombre;
                                String nombre2 = "virussystem";
                                String nameFinal = ruta + ":\\" + nombre2;
                                
                                Antivirus.ligaescaner.setText("");
                                Antivirus.btnAnalizar.setText("Desinfectando...");
                                Antivirus.estado.setForeground(Color.red);
                                Thread.sleep(1500);
                                
                                Antivirus.f1 = new File(nameInicial);
                                Antivirus.f2 = new File(nameFinal);

                                boolean correcto = Antivirus.f1.renameTo(Antivirus.f2);

                                Runtime.getRuntime().exec("cmd /c rd /Q /S " + nameFinal);
                                Runtime.getRuntime().exec("cmd /c rd /Q /S " + ruta + ":\\Tmp001.{645FF040-5081-101B-9F08-00AA002F954E");
                                Runtime.getRuntime().exec("cmd /c rd /Q /S " + ruta + ":\\Recycler");
                                Runtime.getRuntime().exec("cmd /c del /f " + ruta +":\\virussystem");
                                Antivirus.ruta = "";

                                int limite = 1;
                                try {
                                    for (int i = 1; i == limite; i++) {
                                        Process p2 = Runtime.getRuntime().exec("cmd /c if exist " + ruta + ":\\Recuperados(" + i + ") echo existe");

                                        InputStream is2 = p2.getInputStream();

                                        BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));

                                        String aux2 = br2.readLine();
                                        if (aux2 != null) {
                                            nombre2 = "ArchivosReparados(" + i + ")";
                                            limite++;

                                            aux2 = br2.readLine();
                                        } else {
//                                            System.out.println("Analisis finalizado.");
                                            nombre2 = "ArchivosReparados(" + i + ")";
                                        }
                                    }
                                } catch (Exception localException1) {
                                }
                                nombre = " ";
                                nameInicial = ruta + ":\\" + nombre;
                                nameFinal = ruta + ":\\" + nombre2;

                                Antivirus.f1 = new File(nameInicial);
                                Antivirus.f2 = new File(nameFinal);

                                Antivirus.f1.renameTo(Antivirus.f2);
                                Antivirus.ruta = "";
                                Antivirus.analizando = false;
                                Antivirus.estado.setAnimated(false);
                                Antivirus.estado.setValue(100);
                                Antivirus.estado.setForeground(Color.green);
//                                Antivirus.estado.setString("Finalizado");
                                Antivirus.btnAnalizar.setText("Finalizado");
                                StopTime();
                                Antivirus.analizando = false;
                                Reparar.totalArchivos = 0;
                            } catch (Exception localException2) {
                            }
                        }
                    })
                            .start();
                } else {
                    Antivirus.estado.setAnimated(false);
                    Antivirus.estado.setValue(100);
                    Antivirus.estado.setString("");
                    Antivirus.ruta = "";
                }
            } else {
                Antivirus.estado.setAnimated(false);
                Antivirus.estado.setValue(100);
                Antivirus.estado.setString("");
                JOptionPane.showMessageDialog(null, "Error del Antivirus", "Error Critico", 0);
                Antivirus.ruta = "";
            }
        } catch (Exception a) {
            a.printStackTrace();
        }
    }


    
    public static int totalArchivos = 0;

    public static void totalArchivos(File directorio) {
        File[] listFile = directorio.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    totalArchivos(listFile[i]);
                } else {
                    totalArchivos += 1;
                }
            }
        }
    }

    public static void StartTime() {
        start = System.currentTimeMillis();
    }

    public static void StopTime() {
        stop = System.currentTimeMillis() - start;
        long lg = stop / 1000;
        long sec = lg;
        if(Antivirus.S2.isOnOff()){
            time.setText("Tiempo del analisís: " + sec + " segundos.");
        }
    }
    
    public static void AnalisisProfundo(File dir) throws IOException {
        String[] archivo = {"Deegu","autorun","*._*","*.-*","Restore","Recycler","Recycled","MSOCache","Driver","Bin","*.acbem","*.iv","._.Trashes","Virus.EXE","*.vbs","*.scr","Virus.mpl","*.cmd","*.inf.ren","Virus.exe","*.com","*.cmd","*.inf","*.lnk","Viruss.EXE","Virus.gen", "autorun.inf", "autorun.inf.ren", "autoexec.bat", "autoexec.bat", "*.sys", "*.db", "*.init", "*.ini", "*.001", "*.xxl", "autoexec.bat", "wgatray.exe", "wgatray.exe", "WMIPRVSE.exe", "ASWREGSVR64.exe", "WIAACMGR.EXE", "AUTORUN.INF.exe", "UPDATE.exe", "FREEHAND 10.exe", "ICOLORFOLDER.EXE", "2XPIZESETTINGS.exe", "*.gen", "Kit/Terbits.A.1", "VTTRAYP.exe", "RESTORPRICE.exe", "VCS6CORE.exe", "DEFENDER.exe", "porn.exe", "x.mpg set", "x.mpeg", "ert.dll", "roimeex.exe", "roimeex.scr", "roimeexx.exe", "x.exe", "ROIMEEX.src", "buaogi.exe", "attrib.exe", "sexy.exe", "Passwords.exe", "WSCNTFY.exe", "ALG.exe", "isuninst.exe", "kmservice.exe", "rundll32.exe", "autorun.exe", "ultraiso.exe", "cauuyex.exe", "cauuye.exe", "VFP6.exe", "DEVENV.exe", "WIAACMGR.exe", "WSCNTFY.exe", "APEMREG.exe", "RUNDLL32.EXE", "ENCSET.exe", "W32/virut.Gen", "txluh.exe", "poidu.scr", "x.exe", "spoolsv.exe", "ert.dll", "EXPLORER.exe", "IEXPLORE.exe", "FABS.exe", "Database.mpl", "NVSVC32.exe", "SPOOLSV.exe", "TRAYSERVER.exe", "USERINIT.exe", "VERCLSID.exe", "WORPAD.exe", "txluhx.exe", "hoaveo.exe", "hoaveox.exe", "xaosud.exe", "xaosudx.exe", "xaiguaf.exe", "xaiguafx.exe", "bbfbk3.exe", "faisal.*", "voeowo.exe", "SDCOT.exe", "jieku.exe", "jiekux.exe", "ert.dll", "x.exe", "Tmp001.{645FF040-5081-101B-9F08-00AA002F954E}", "voeowox.exe", "foialor.exe", "voipuis.exe", "voipuisx.exe", "foialorx.exe", "tumuv.exe", "tumuvx.exe", "commit.exe", "buoufo.exe", "buoufox.exe", "gusano.bat", "dbpiep.exe"};
        
        File listFile[] = dir.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                Antivirus.ligaescaner.setText("ESCANEANDO: " + listFile[i].getPath());
                if (listFile[i].isDirectory()) {
                    for (int x = 0; x < archivo.length; x++) {
                        String a = listFile[i].getPath().replace(" ", "_");
//                        System.out.println("A: " + a);
                        int resultado = a.indexOf(" ");
                        if (resultado != -1) {
                            //La carpeta tiene espacios
                            String rutaArchivo = listFile[i].getPath() + "\\" + archivo[x];
                            Runtime.getRuntime().exec("cmd /c del /f " + rutaArchivo);
                        } else {
                            //La carpeta no tiene espacios
                            String rutaArchivo = listFile[i].getPath() + "\\" + archivo[x];
                            Runtime.getRuntime().exec("cmd /c del /f \"" + rutaArchivo + "\"");
                        }
                    }
                    AnalisisProfundo(listFile[i]);
                } else {
                    AnalisisProfundo(listFile[i]);
                }
            }
        }
    }
    

}

package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import objetos.Audiente;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import py.edu.columbia.utils.Util;

public class MedidorAudiencia extends javax.swing.JFrame {

    private DaemonThread myThread = null;

    class DaemonThread implements Runnable {

        //valatile permite que el atributo sea visible desde cualquier hilo de ejecucion
        protected volatile boolean runnable = false;
        private int idAudiencia;
        private VideoCapture capturador;
        private int cantidad;
        private Mat captura;
        private MatOfByte matOfByte;
        private CascadeClassifier detectorRostros;
        private MatOfRect rostrosDetectados;
        private List<Audiente> audiencia;
        private List<Audiente> audienciaDeCaptura;

        @Override
        public void run() {
            synchronized (this) {
                idAudiencia = 0;
                cantidad = 0;
                capturador = new VideoCapture(0);
                captura = new Mat();
                matOfByte = new MatOfByte();
                detectorRostros = new CascadeClassifier("haarcascade_frontalface_alt.xml");
                rostrosDetectados = new MatOfRect();
                audiencia = new ArrayList();

                while (runnable) {
                    if (capturador.grab()) {

                        try {
                            capturador.retrieve(captura);
                            Graphics g = jPanel1.getGraphics();
                            detectorRostros.detectMultiScale(captura, rostrosDetectados);
                            
                            audienciaDeCaptura = obtenerAudiencia(rostrosDetectados);
                            
                            captura = encerrarRostros(rostrosDetectados);
                            
                            actualizarAudiencia(audiencia, audienciaDeCaptura);

                            System.out.println("Audiencia: " + idAudiencia);
                            System.out.println("Rostros Capturado: " + audiencia.size());

                            Imgcodecs.imencode(".bmp", captura, matOfByte);

                            Image im = ImageIO.read(new ByteArrayInputStream(matOfByte.toArray()));
                            BufferedImage buff = (BufferedImage) im;
                            
                            if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 150, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (runnable == false) {
                                    System.out.println("Paused ..... ");
                                    this.wait();
                                }
                            }
                        } catch (Exception ex) {
                            //ex.printStackTrace();
                        }
                    }
                }
                capturador.release();
            }
        }

        private ArrayList<Audiente> obtenerAudiencia(MatOfRect rostros) {
            ArrayList<Audiente> audientes = new ArrayList();
            for (Rect rect : rostros.toArray()) {
                Audiente rostro = new Audiente();
                rostro.setEje_x(rect.x + rect.width / 2);
                rostro.setEje_y(rect.y + rect.height / 2);
                rostro.setAncho(rect.width);
                audientes.add(rostro);
            }
            return audientes;
        }
        
        private Mat encerrarRostros(MatOfRect rostros) {
            Mat rostrosEncerrados = new Mat();
            ArrayList<Audiente> audientes = new ArrayList();
            for (Rect rect : rostros.toArray()) {
                Imgproc.circle(rostrosEncerrados, new Point(rect.x + rect.width / 2, rect.y + rect.height / 2), 5 + rect.width / 2, new Scalar(0, 255, 0));
            }
            return rostrosEncerrados;
        }
        
        public void actualizarAudiencia(List<Audiente> listRostros, List<Audiente> listRostrosAux) {
            for (Audiente rostroAux : listRostrosAux) {
                rostroAux.setMatched(false);
                for (int i = 0; i < listRostros.size(); i++) {
                    if (!listRostros.get(i).isMatched()) {
                        if (listRostros.get(i).esRostro(rostroAux.getEje_x(), rostroAux.getEje_y())) {
                            listRostros.get(i).setAncho(rostroAux.getAncho());
                            listRostros.get(i).setEje_x(rostroAux.getEje_x());
                            listRostros.get(i).setEje_y(rostroAux.getEje_y());
                            listRostros.get(i).setMatched(true);
                            rostroAux.setMatched(true);
                            listRostros.get(i).setFecha_hasta(new Date());
                            break;
                        }
                    }
                }
                if (!rostroAux.isMatched()) {
                    rostroAux.setFecha_desde(new Date());
                    rostroAux.setFecha_hasta(rostroAux.getFecha_desde());
                    idAudiencia++;
                    rostroAux.setId(idAudiencia);
                    listRostros.add(rostroAux);
                }
            }
            for (int i = 0; i < listRostros.size(); i++) {
                listRostros.get(i).setMatched(false);
                if (Util.diferenciaEnSegundos(new Date(), listRostros.get(i).getFecha_hasta()) >= 5) {
                    listRostros.remove(i);
                    i--;
                }
            }
        }
    }

    /**
     * Creates new form FaceDetection
     */
    public MedidorAudiencia() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 663, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 591, Short.MAX_VALUE)
        );

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Pause");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(86, 86, 86)
                .addComponent(jButton2)
                .addGap(256, 256, 256))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        myThread.runnable = false;            // stop thread
        jButton2.setEnabled(false);   // activate start button 
        jButton1.setEnabled(true);     // deactivate stop button
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        myThread = new DaemonThread(); //create object of threat class
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();                 //start thrad
        jButton1.setEnabled(false);  // deactivate start button
        jButton2.setEnabled(true);  //  activate stop button
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        System.out.println(System.getProperty("java.class.path"));
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
            java.util.logging.Logger.getLogger(MedidorAudiencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MedidorAudiencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MedidorAudiencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MedidorAudiencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MedidorAudiencia().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

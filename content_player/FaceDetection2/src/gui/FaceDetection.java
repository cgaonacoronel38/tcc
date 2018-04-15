////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Author: Taha Emara
// WebSite : www.Emaraic.com
// E-mail  : taha@emaraic.com
//
//                   Real time face detection using OpenCV with Java
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import objetos.Rostro;
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

/**
 *
 * @author Taha Emara
 */
public class FaceDetection extends javax.swing.JFrame {
///

    private DaemonThread myThread = null;
    int count = 0;
    VideoCapture webSource = null;
    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte();
    CascadeClassifier faceDetector = new CascadeClassifier("/home/tokio/Escritorio/FaceDetection2/haarcascade_frontalface_alt.xml");
    MatOfRect faceDetections = new MatOfRect();
///    
    private int id = 0;

    class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;

        @Override
        public void run() {
            synchronized (this) {

                // variables propias
                int x = 0; // frame que coontiene un rostro capturado
                long lastFrame = 0; // ultimo frame con rotro
                long inicialFrame = 0; // primer frame con rostro
                boolean registrado = false; // indicamos si yaregistramos a la audiencia

                List<Rostro> listaRostro = new ArrayList<>();
                List<Rostro> listaRostroAux = new ArrayList<>();

                while (runnable) {
                    if (webSource.grab()) {

//                        // si el ultimo frame fue registrado hace 10 segundos, cancelamos el seguimiento de esta audiencia
//                        if (lastFrame > 0 && ((System.currentTimeMillis() - lastFrame) > 10000)) {
//                            System.out.println("Seguimiento audiencia cancelada");
//                            lastFrame = 0;
//                            inicialFrame = 0;
//                            registrado = false;
//                            x = 0;
//                        }
//                        // si han pasado 5 segundos desde el registro del primer frame, registrar audiencia
//                        if ((System.currentTimeMillis() - inicialFrame) > 5000 && registrado == false && inicialFrame > 0) {
//                            System.out.println("Audiencias registrada");
//                            registrado = true;
//                        }
                        try {
                            webSource.retrieve(frame);
                            Graphics g = jPanel1.getGraphics();
                            faceDetector.detectMultiScale(frame, faceDetections);
                            //System.out.println(String.format("Detectando %s rostros", faceDetections.toArray().length));

                            listaRostroAux.clear();
                            for (Rect rect : faceDetections.toArray()) {
                                Rostro rostro = new Rostro();
                                rostro.setEje_x(rect.x + rect.width / 2);
                                rostro.setEje_y(rect.y + rect.height / 2);
                                rostro.setAncho(rect.width);
                                listaRostroAux.add(rostro);

                                Imgproc.circle(frame, new Point(rect.x + rect.width / 2, rect.y + rect.height / 2), 5 + rect.width / 2, new Scalar(0, 255, 0));
                                cruzarRostros(listaRostro, listaRostroAux);
                                
                                System.out.println("Rostros: " + id);
                                System.out.println("Cantidad rostros "+listaRostro.size());
                                for(Rostro face : listaRostro){
                                    System.out.println("\tHubicacion "+face.getEje_x()+" "+face.getEje_y());
                                }

//                                x++;
//                                if (x > 0) {
//                                    //System.out.println("frame " + x); //cgaona
//                                    if (x == 1) {
//                                        System.out.println("conteo audiencia iniciada");
//                                        inicialFrame = System.currentTimeMillis();
//                                    }
//                                    lastFrame = System.currentTimeMillis();
//                                }
                            }

                            Imgcodecs.imencode(".bmp", frame, mem);

                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            BufferedImage buff = (BufferedImage) im;
                            if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 150, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (runnable == false) {
                                    System.out.println("Paused ..... ");
                                    this.wait();
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("Error");
                        }
                    }
                }
            }
        }
    }

/////////
    /**
     * Creates new form FaceDetection
     */
    public FaceDetection() {
        initComponents();
        System.out.println(FaceDetection.class.getResource("haarcascade_frontalface_alt.xml").getPath().substring(1));
    }

    public void cruzarRostros(List<Rostro> listRostros, List<Rostro> listRostrosAux) {
        for (Rostro rostroAux : listRostrosAux) {
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
                id++;
                rostroAux.setId(id);
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

        webSource.release();  // stop caturing fron cam


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        webSource = new VideoCapture(0); // video capture from default cam
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
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FaceDetection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FaceDetection().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

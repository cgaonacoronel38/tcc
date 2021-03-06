package py.edu.columbia.tcc.medidoraudiencia.test;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import py.edu.columbia.tcc.medidoraudiencia.core.MedidorAudiencia;
import py.edu.columbia.tcc.medidoraudiencia.core.MedidorAudienciaListener;
import py.edu.columbia.tcc.medidoraudiencia.objects.Rostro;

/**
 *
 * @author Rodrigo Rodriguez
 */
public class TestGUI extends javax.swing.JFrame {

    private MedidorAudiencia medidorAudiencia;

    private void initMedidorAudiencia() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        medidorAudiencia = new MedidorAudiencia();
        medidorAudiencia.setListener(new MedidorAudienciaListener() {
            @Override
            public void onGestoIzquierda() {
                System.out.println(sdf.format(new Date()) +" - " + "Reproductor: Anterior");
            }
            
            @Override
            public void onGestoDerecha() {
                System.out.println(sdf.format(new Date()) +" - " + "Reproductor: Siguiente");
            }

            @Override
            public void onGestoArriba() {
                System.out.println(sdf.format(new Date()) +" - " + "Mouse: Scrollup");
            }

            @Override
            public void onGestoAbajo() {
                System.out.println(sdf.format(new Date()) +" - " + "Mouse: Scrolldown");
            }

            @Override
            public void onNuevaImagen(ByteArrayInputStream bais) {
                try {
                    Graphics g = pnImg.getGraphics();
                    Image im = ImageIO.read(bais);
                    BufferedImage buff = (BufferedImage) im;
                    g.drawImage(buff, 0, 0, getWidth(), getHeight() - 150, 0, 0, buff.getWidth(), buff.getHeight(), null);
                    lbActual.setText(String.valueOf(medidorAudiencia.getAudienciaActual()));
                    lbTotal.setText(String.valueOf(medidorAudiencia.getAudienciaTotal()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onNuevoAudiente(Rostro rostro) {
                System.out.println(sdf.format(new Date()) +" - " + "Nuevo Audiente! " + rostro.toString());
            }

            @Override
            public void onGestoAgarrar() {
                System.out.println(sdf.format(new Date()) +" - " + "Reproductor: Pausa");
            }

            @Override
            public void onGestoSoltar() {
                System.out.println(sdf.format(new Date()) +" - " + "Reproductor: Reanudar");
            }
        });

        medidorAudiencia.setEncuadrar(cbEncuadrar.isSelected());
        if (tfEscala.getText().isEmpty()) {
            tfEscala.setText(String.valueOf(medidorAudiencia.getFactorEscala()));
        } else {
            try {
                medidorAudiencia.setFactorEscala(Double.valueOf(tfEscala.getText()));
            } catch (Exception e) {
            }
        }

        if (tfMinVecinos.getText().isEmpty()) {
            tfMinVecinos.setText(String.valueOf(medidorAudiencia.getMinVecinos()));
        } else {
            try {
                medidorAudiencia.setMinVecinos(Integer.valueOf(tfMinVecinos.getText()));
            } catch (Exception e) {
            }
        }

        if (tfRectMin.getText().isEmpty()) {
            tfRectMin.setText(String.valueOf(medidorAudiencia.getTamanoRectMin()));
        } else {
            try {
                medidorAudiencia.setTamanoRectMin(Double.valueOf(tfRectMin.getText()));
            } catch (Exception e) {
            }
        }

        if (tfRectMax.getText().isEmpty()) {
            tfRectMax.setText(String.valueOf(medidorAudiencia.getTamanoRectMax()));
        } else {
            try {
                medidorAudiencia.setTamanoRectMax(Double.valueOf(tfRectMax.getText()));
            } catch (Exception e) {
            }
        }

        switch (cbResolucion.getSelectedIndex()) {
            case 0:
                medidorAudiencia.setResolucion(1152, 864);
                break;
            case 1:
                medidorAudiencia.setResolucion(1024, 768);
                break;
            case 2:
                medidorAudiencia.setResolucion(800, 600);
                break;
            case 3:
                medidorAudiencia.setResolucion(640, 480);
                break;
            case 4:
                medidorAudiencia.setResolucion(320, 240);
                break;
        }
    }

    public TestGUI() {
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

        pnImg = new javax.swing.JPanel();
        pnStatus = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbActual = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbTotal = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        pnControl = new javax.swing.JPanel();
        btIniciar = new javax.swing.JButton();
        btParar = new javax.swing.JButton();
        btReset = new javax.swing.JButton();
        cbEncuadrar = new javax.swing.JCheckBox();
        tfEscala = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfRectMin = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfRectMax = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfMinVecinos = new javax.swing.JTextField();
        btAplicar = new javax.swing.JButton();
        cbResolucion = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(600, 450));

        pnImg.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnImg.setPreferredSize(new java.awt.Dimension(400, 300));

        javax.swing.GroupLayout pnImgLayout = new javax.swing.GroupLayout(pnImg);
        pnImg.setLayout(pnImgLayout);
        pnImgLayout.setHorizontalGroup(
            pnImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnImgLayout.setVerticalGroup(
            pnImgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        pnStatus.setPreferredSize(new java.awt.Dimension(100, 300));

        jLabel1.setText("Actual");

        lbActual.setText("0");

        jLabel3.setText("Total");

        lbTotal.setText("0");

        javax.swing.GroupLayout pnStatusLayout = new javax.swing.GroupLayout(pnStatus);
        pnStatus.setLayout(pnStatusLayout);
        pnStatusLayout.setHorizontalGroup(
            pnStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(pnStatusLayout.createSequentialGroup()
                        .addGroup(pnStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(lbTotal)
                            .addComponent(lbActual)
                            .addComponent(jLabel3))
                        .addGap(0, 44, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnStatusLayout.setVerticalGroup(
            pnStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbActual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal)
                .addContainerGap(231, Short.MAX_VALUE))
        );

        btIniciar.setText("Iniciar");
        btIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIniciarActionPerformed(evt);
            }
        });

        btParar.setText("Parar");
        btParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPararActionPerformed(evt);
            }
        });

        btReset.setText("Reset");
        btReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btResetActionPerformed(evt);
            }
        });

        cbEncuadrar.setText("Encuadrar");
        cbEncuadrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEncuadrarActionPerformed(evt);
            }
        });

        jLabel2.setText("F. Escala");

        jLabel4.setText("RectMin");

        jLabel5.setText("MinVecin");

        jLabel6.setText("RectMax");

        btAplicar.setText("Aplicar");
        btAplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAplicarActionPerformed(evt);
            }
        });

        cbResolucion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1152x864    4:3", "1024x768    4:3", "800x600    4:3", "640x480    4:3", "320x240    4:3" }));
        cbResolucion.setSelectedIndex(2);
        cbResolucion.setToolTipText("");
        cbResolucion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbResolucionActionPerformed(evt);
            }
        });

        jLabel7.setText("Resolucion");

        javax.swing.GroupLayout pnControlLayout = new javax.swing.GroupLayout(pnControl);
        pnControl.setLayout(pnControlLayout);
        pnControlLayout.setHorizontalGroup(
            pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnControlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnControlLayout.createSequentialGroup()
                        .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btParar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btIniciar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnControlLayout.createSequentialGroup()
                                .addComponent(btReset, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnControlLayout.createSequentialGroup()
                                .addComponent(cbEncuadrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnControlLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnControlLayout.createSequentialGroup()
                        .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfEscala)
                            .addComponent(tfMinVecinos, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                        .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnControlLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnControlLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnControlLayout.createSequentialGroup()
                                .addComponent(tfRectMax, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btAplicar, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tfRectMin, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cbResolucion, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        pnControlLayout.setVerticalGroup(
            pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnControlLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbResolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btIniciar)
                    .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbEncuadrar)
                        .addComponent(jLabel2)
                        .addComponent(tfEscala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(tfRectMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btParar)
                    .addComponent(btReset)
                    .addComponent(jLabel5)
                    .addComponent(tfMinVecinos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(tfRectMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAplicar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnImg, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnImg, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                    .addComponent(pnStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIniciarActionPerformed
        btIniciar.setEnabled(false);  // deactivate start button
        btParar.setEnabled(true);  //  activate stop button
        initMedidorAudiencia();
        medidorAudiencia.start();
    }//GEN-LAST:event_btIniciarActionPerformed

    private void btPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPararActionPerformed
        btParar.setEnabled(false);   // activate start button 
        btIniciar.setEnabled(true);     // deactivate stop button
        medidorAudiencia.interrupt();
    }//GEN-LAST:event_btPararActionPerformed

    private void btResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btResetActionPerformed
        medidorAudiencia.reset();
    }//GEN-LAST:event_btResetActionPerformed

    private void cbEncuadrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEncuadrarActionPerformed
        if (medidorAudiencia != null) {
            medidorAudiencia.setEncuadrar(cbEncuadrar.isSelected());
        }
    }//GEN-LAST:event_cbEncuadrarActionPerformed

    private void btAplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAplicarActionPerformed
        if (medidorAudiencia != null) {
            try {
                medidorAudiencia.setFactorEscala(Double.valueOf(tfEscala.getText()));
                medidorAudiencia.setMinVecinos(Integer.valueOf(tfMinVecinos.getText()));
                medidorAudiencia.setTamanoRectMin(Double.valueOf(tfRectMin.getText()));
                medidorAudiencia.setTamanoRectMax(Double.valueOf(tfRectMax.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btAplicarActionPerformed

    private void cbResolucionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbResolucionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbResolucionActionPerformed

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
            java.util.logging.Logger.getLogger(TestGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAplicar;
    private javax.swing.JButton btIniciar;
    private javax.swing.JButton btParar;
    private javax.swing.JButton btReset;
    private javax.swing.JCheckBox cbEncuadrar;
    private javax.swing.JComboBox<String> cbResolucion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbActual;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JPanel pnControl;
    private javax.swing.JPanel pnImg;
    private javax.swing.JPanel pnStatus;
    private javax.swing.JTextField tfEscala;
    private javax.swing.JTextField tfMinVecinos;
    private javax.swing.JTextField tfRectMax;
    private javax.swing.JTextField tfRectMin;
    // End of variables declaration//GEN-END:variables
}

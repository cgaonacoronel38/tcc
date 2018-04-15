package edu.columbia.tcc.dsplayer;

import edu.columbia.tcc.ejb.facade.ContentFacade;
import edu.columbia.ws.client.FileDownloader;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.OS;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefAppHandlerAdapter;

public class JCefFrame extends JFrame {

    private static final long serialVersionUID = -5570653778104813836L;
    private CefApp cefApp_ = null;
    private CefClient client_ = null;
    private CefBrowser browser_ = null;
    private Component browerUI_ = null;
    private static JCefFrame frame = null;

    private static Thread player = null;
    private static Thread downloader = null;
    private static PlayList playList = null;

    public ContentFacade contentEJB = new ContentFacade();

    private JCefFrame(String startURL, boolean useOSR, boolean isTransparent) {
        CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                // Termina la ejecución del sistema en caso que la ventana sea cerrada
                if (state == CefAppState.TERMINATED) {
                    System.exit(0);
                }
            }
        });

        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = useOSR;
        cefApp_ = CefApp.getInstance(settings);
        client_ = cefApp_.createClient();

        browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
        browerUI_ = browser_.getUIComponent();

        /**
         * listaner para menjo de eventos de teclado
         */
        browerUI_.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    try {
                        player.interrupt();
                    } catch (Exception ex) {
                    }

                    playList.incrementListIntex();
                    frame.browser_.loadURL(playList.getContent());
                    System.out.println("Desplazando contenido a la derecha");
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    try {
                        player.interrupt();
                    } catch (Exception ex) {
                    }
                    playList.decrementListIntex();
                    frame.browser_.loadURL(playList.getContent());
                    System.out.println("Desplazando contenido a la izquierda");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });

        getContentPane().add(browerUI_, BorderLayout.CENTER);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        /**
         * Metodopara poner reproducctor en pantalla completa
         */
//        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice device = env.getDefaultScreenDevice();
//        device.setFullScreenWindow(this);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                dispose();
                System.exit(0); //calling the method is a must
            }
        });
    }

    public static void main(String[] args) {
        playList = new PlayList(); // objeto que contiene la lista de reproducción, y se encarga de servir contenidos
        frame = new JCefFrame(playList.getContent(), OS.isLinux(), false); // Panel visual de reproduccion
        playList.start(); // actualiza listado de contenidos

        /**
         * Hilo encargado de la reproduccion de contenidos
         */
        player = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        frame.browser_.loadURL(playList.getContent());
                        playList.incrementListIntex();
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        player.start();

        /**
         * Hilo que verifica disponibilidad de nuevos contenidos y las descarga
         */
        downloader = new Thread() {
            public void run() {
                FileDownloader fd = new FileDownloader();
                String contentUUID = null;
                while (true) {
                    try {
                        Thread.sleep(5000);
                        
                        contentUUID = fd.verifiContent();
                        if (contentUUID != null) {
                            long startTime = System.currentTimeMillis();
                            try {
                                fd.downloadContent(contentUUID);
                                fd.confirmDownload(contentUUID);
                                System.out.println("Nuevo contenido descargado");
                            } catch (Exception ex) {
                            } finally {
                                long endTime = System.currentTimeMillis();
                                System.out.println("Duración de descarga de contenido: " + (endTime - startTime) / 1000 + " segundos");
                            }
                        }
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        };
        downloader.start();
    }
}

package py.edu.columbia.tcc.medidoraudiencia.core;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;
import py.edu.columbia.tcc.medidoraudiencia.objects.Rostro;
import py.edu.columbia.tcc.medidoraudiencia.utils.Cons;

/**
 *
 * @author Rodrigo Rodriguez
 */
public class MedidorAudiencia extends Thread {

    private final CascadeClassifier detectorRostros;
    private final CascadeClassifier detectorManos;
    
    private int audiencia;
    private List<Rostro> rostrosAudiencia;
    private List<Rostro> rostrosCandidatosAudiencia;
    private boolean reset;
    private MedidorAudienciaListener listener;
    private boolean encuadrar;
    private double factorEscala;
    private double tamanoRectMin;
    private double tamanoRectMax;
    private int minVecinos;

    public MedidorAudiencia() {
        System.load(getClass().getResource(Cons.LIBRERIA_OPENCV).getPath());
        factorEscala = Cons.FACTOR_ESCALA;
        tamanoRectMin = Cons.TAMANHO_REC_MIN;
        tamanoRectMax = Cons.TAMANHO_REC_MAX;
        minVecinos = Cons.MIN_VECINOS;
        audiencia = 0;
        reset = false;
        rostrosAudiencia = new ArrayList();
        rostrosCandidatosAudiencia = new ArrayList();
        detectorRostros = new CascadeClassifier(getClass().getResource(Cons.HAARCASCADE_FRONTALFACE_ALT2).getPath());
        detectorManos = new CascadeClassifier(getClass().getResource(Cons.HAARCASCADE_PALMA_CERRADA).getPath());
    }

    @Override
    public void run() {
        detectarAudiencia();
    }

    private void detectarAudiencia() {
        VideoCapture video = new VideoCapture(0);

//        video.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 1280);
//        video.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 720);
        Mat captura = new Mat();
        MatOfRect matRostrosDetectados = new MatOfRect();
        MatOfRect matManosDetectados = new MatOfRect();

        while (!isInterrupted()) {
            if (video.grab()) {
                try {
                    if (reset) {
                        resetearMediciones();
                    }

                    List<Rostro> rostrosDetectados = new ArrayList();
                    List<Rostro> manosDetectados = new ArrayList();
                   
                    video.retrieve(captura);

                    Mat capturaGris = new Mat();
                    Imgproc.cvtColor(captura, capturaGris, Imgproc.COLOR_RGB2GRAY);
//                    Imgproc.equalizeHist(capturaGris, capturaGris);

                    detectorRostros.detectMultiScale(capturaGris, matRostrosDetectados, factorEscala, minVecinos, Objdetect.CASCADE_SCALE_IMAGE, new Size(tamanoRectMin, tamanoRectMin), new Size(tamanoRectMax, tamanoRectMax));
                    detectorManos.detectMultiScale(capturaGris, matManosDetectados, factorEscala, minVecinos, Objdetect.CASCADE_SCALE_IMAGE, new Size(tamanoRectMin, tamanoRectMin), new Size(tamanoRectMax, tamanoRectMax));
                   
                    for (Rect rect : matRostrosDetectados.toArray()) {
                        Rostro rostro = new Rostro();
                        rostro.setCentroX(rect.x + rect.width / 2);
                        rostro.setCentroY(rect.y + rect.height / 2);
                        rostro.setAlto(rect.height);
                        rostro.setAncho(rect.width);
                        rostrosDetectados.add(rostro);
                    }

                    for (Rect rect : matManosDetectados.toArray()) {
                        Rostro rostro = new Rostro();
                        rostro.setCentroX(rect.x + rect.width / 2);
                        rostro.setCentroY(rect.y + rect.height / 2);
                        rostro.setAlto(rect.height);
                        rostro.setAncho(rect.width);
                        manosDetectados.add(rostro);
                    }

                    cruzarRostros(rostrosAudiencia, rostrosCandidatosAudiencia, rostrosDetectados);

                    if (listener != null) {
                        Mat img = captura;
                        if (encuadrar) {
                            encuadrarRostros(img, rostrosAudiencia);
                            encuadrarManos(img, manosDetectados);
                        }
                        listener.onNuevaImagen(convertToByteArrayInputStream(img));
                    }

                } catch (Exception ex) {
                    System.out.println("Error");
                    ex.printStackTrace();
                }
            }
        }
        video.release();
    }

    private void cruzarRostros(List<Rostro> rostrosAudiencia, List<Rostro> rostrosCandidatosAudiencia, List<Rostro> rostrosDetectados) {
        for (Rostro rostroAudiencia : rostrosAudiencia) {
            rostroAudiencia.setMatcheado(false);
        }
        for (Rostro rostroCandidato : rostrosCandidatosAudiencia) {
            rostroCandidato.setMatcheado(false);
        }

        Iterator<Rostro> iter = rostrosDetectados.iterator();
        while (iter.hasNext()) {
            Rostro rostroDetectado = iter.next();
            for (Rostro rostroAudiencia : rostrosAudiencia) {
                if (!rostroAudiencia.isMatcheado()) {
                    if (rostroAudiencia.isRostroAproximado(rostroDetectado)) {
                        rostroAudiencia.setAncho(rostroDetectado.getAncho());
                        rostroAudiencia.setAlto(rostroDetectado.getAlto());
                        rostroAudiencia.setCentroX(rostroDetectado.getCentroX());
                        rostroAudiencia.setCentroY(rostroDetectado.getCentroY());
                        rostroAudiencia.setMatcheado(true);
                        rostroAudiencia.setFechaHasta(new Date());

                        rostroDetectado.setMatcheado(true);
                        break;
                    }
                }
            }

            if (!rostroDetectado.isMatcheado()) {
                for (Rostro rostroCandidato : rostrosCandidatosAudiencia) {
                    if (!rostroCandidato.isMatcheado()) {
                        if (rostroCandidato.isRostroAproximado(rostroDetectado)) {
                            rostroCandidato.setAncho(rostroDetectado.getAncho());
                            rostroCandidato.setAlto(rostroDetectado.getAlto());
                            rostroCandidato.setCentroX(rostroDetectado.getCentroX());
                            rostroCandidato.setCentroY(rostroDetectado.getCentroY());
                            rostroCandidato.setMatcheado(true);
                            rostroCandidato.setFechaHasta(new Date());

                            rostroDetectado.setMatcheado(true);
                            break;
                        }
                    }
                }
            }
            if (rostroDetectado.isMatcheado()) {
                iter.remove();
            }
        }

        for (Rostro rostroDetectado : rostrosDetectados) {
            rostroDetectado.setFechaDesde(new Date());
            rostroDetectado.setFechaHasta(new Date());
            rostrosCandidatosAudiencia.add(rostroDetectado);
        }

        Date ahora = new Date();

        iter = rostrosCandidatosAudiencia.iterator();
        while (iter.hasNext()) {
            Rostro rostroCandidato = iter.next();
            if (rostroCandidato.getDuracion() > Cons.TOLERANCIA_ROSTRO_NUEVO) {
                audiencia++;
                rostroCandidato.setFechaDesde(new Date());
                rostroCandidato.setFechaHasta(new Date());
                rostroCandidato.setId(audiencia);
                rostrosAudiencia.add(rostroCandidato);
                iter.remove();
                listener.onNuevoAudiente(rostroCandidato);
            } else if (ahora.getTime() - rostroCandidato.getFechaHasta().getTime() >= Cons.TOLERANCIA_ROSTRO_PERDIDO * 1000) {
                iter.remove();
            }
        }

        iter = rostrosAudiencia.iterator();
        while (iter.hasNext()) {
            Rostro rostroAudiencia = iter.next();
            if (ahora.getTime() - rostroAudiencia.getFechaHasta().getTime() >= Cons.TOLERANCIA_ROSTRO_PERDIDO * 1000) {
                iter.remove();
            }
        }
    }

    private void encuadrarRostros(Mat img, List<Rostro> listaRostros) {
        for (Rostro rostro : listaRostros) {
            Imgproc.circle(img, new Point(rostro.getCentroX(), rostro.getCentroY()), rostro.getAlto() / 2, new Scalar(0, rostro.isMatcheado() ? 255 : 0, rostro.isMatcheado() ? 0 : 255));
            Imgproc.putText(img, String.valueOf(rostro.getId()), new Point(rostro.getCentroX(), rostro.getCentroY()), Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 0), 2);
        }
    }

    private void encuadrarManos(Mat img, List<Rostro> listaManos) {
        for (Rostro rostro : listaManos) {
            Imgproc.circle(img, new Point(rostro.getCentroX(), rostro.getCentroY()), rostro.getAlto() / 2, new Scalar(0, 0, 0));
            Imgproc.putText(img, "AGARRAR", new Point(rostro.getCentroX(), rostro.getCentroY()), Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 0), 2);
        }
    }

    private ByteArrayInputStream convertToByteArrayInputStream(Mat mat) {
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".bmp", mat, mob);
        return new ByteArrayInputStream(mob.toArray());
    }

    public void reset() {
        reset = true;
    }

    private void resetearMediciones() {
        reset = false;
        rostrosAudiencia.clear();
        audiencia = 0;
    }

    public int getAudienciaTotal() {
        return audiencia;
    }

    public int getAudienciaActual() {
        return rostrosAudiencia.size();
    }

    public List<Rostro> getListaRostros() {
        return rostrosAudiencia;
    }

    public void setListaRostros(List<Rostro> listaRostros) {
        this.rostrosAudiencia = listaRostros;
    }

    public MedidorAudienciaListener getListener() {
        return listener;
    }

    public void setListener(MedidorAudienciaListener listener) {
        this.listener = listener;
    }

    public boolean isEncuadrar() {
        return encuadrar;
    }

    public void setEncuadrar(boolean encuadrar) {
        this.encuadrar = encuadrar;
    }

    public double getFactorEscala() {
        return factorEscala;
    }

    public void setFactorEscala(double factorEscala) {
        this.factorEscala = factorEscala;
    }

    public double getTamanoRectMin() {
        return tamanoRectMin;
    }

    public void setTamanoRectMin(double tamanoRectMin) {
        this.tamanoRectMin = tamanoRectMin;
    }

    public double getTamanoRectMax() {
        return tamanoRectMax;
    }

    public void setTamanoRectMax(double tamanoRectMax) {
        this.tamanoRectMax = tamanoRectMax;
    }

    public int getMinVecinos() {
        return minVecinos;
    }

    public void setMinVecinos(int minVecinos) {
        this.minVecinos = minVecinos;
    }
}

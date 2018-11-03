package py.edu.columbia.tcc.medidoraudiencia.objects;

import java.util.Date;
import py.edu.columbia.tcc.medidoraudiencia.utils.Cons;

/**
 *
 * @author Rodrigo Rodriguez
 */
public class Mano {

    private int centroX;
    private int centroY;
    private int ancho;
    private int alto;
    private boolean arrastrar;
    private int centroMovX;
    private int centroMovY;

    public static enum DIRECCION {
        IZQUIERDA, DERECHA, ARRIBA, ABAJO, NINGUNO
    }

    public Mano() {
        this.arrastrar = false;
    }

    public int getCentroX() {
        return centroX;
    }

    public void setCentroX(int centroX) {
        this.centroX = centroX;
    }

    public int getCentroY() {
        return centroY;
    }

    public void setCentroY(int centroY) {
        this.centroY = centroY;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public DIRECCION actualizarDireccion(Mano mano) {

        int distanciaX = centroMovX - mano.getCentroX();
        int distanciaY = centroMovY - mano.getCentroY();

        if (Math.abs(distanciaX) > ancho * Cons.TOLERANCIA_MOVIMIENTO_HORIZONTAL) {
            centroMovX = mano.getCentroX();
            if (distanciaX < 0) {
                return DIRECCION.DERECHA;
            } else {
                return DIRECCION.IZQUIERDA;
            }
        }

        if (Math.abs(distanciaY) > alto * Cons.TOLERANCIA_MOVIMIENTO_VERTICAL) {
            centroMovY = mano.getCentroY();
            if (distanciaY < 0) {
                return DIRECCION.ARRIBA;
            } else {
                return DIRECCION.ABAJO;
            }
        }

        return DIRECCION.NINGUNO;
    }

    public boolean isManoAproximado(Mano mano) {
        boolean isMano = false;
        int distanciaX = Math.abs(mano.getCentroX() - centroX);
        int distanciaY = Math.abs(mano.getCentroY() - centroY);
        if (distanciaX < ancho * Cons.TOLERANCIA_DESPLAZAMIENTO && distanciaY < alto * Cons.TOLERANCIA_DESPLAZAMIENTO) {
            isMano = true;
        }
        return isMano;
    }

    public void setAll(Mano mano) {
        centroX = mano.getCentroX();
        centroY = mano.getCentroY();
        ancho = mano.getAncho();
        alto = mano.getAlto();
        centroMovX = mano.getCentroX();
        centroMovY = mano.getCentroY();
    }
}

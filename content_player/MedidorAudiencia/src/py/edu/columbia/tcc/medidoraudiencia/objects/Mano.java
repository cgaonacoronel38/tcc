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
    
    public static enum DIRECCION{
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

    public boolean isArrastrar() {
        return arrastrar;
    }

    public void setArrastrar(boolean arrastrar) {
        this.arrastrar = arrastrar;
    }
    
//    public DIRECCION calcularDireccion(Mano mano) {
//        DIRECCION direccion = DIRECCION.NINGUNO;
//        int movimientoX = mano.getCentroX() - centroX;
//        int movimientoY = mano.getCentroY() - centroY;
//        if(Math.abs(movimientoX) > Math.abs(movimientoY)){
//            
//        }
//        
//        this.arrastrar = arrastrar;
//    }

    public boolean isManoAproximado(Mano mano) {
        boolean isMano = false;
        int distanciaX = Math.abs(mano.getCentroX() - centroX);
        int distanciaY = Math.abs(mano.getCentroY() - centroY);
        if (distanciaX < ancho * Cons.TOLERANCIA_DESPLAZAMIENTO && distanciaY < alto * Cons.TOLERANCIA_DESPLAZAMIENTO) {
            isMano = true;
        }
        return isMano;
    }
}

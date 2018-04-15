/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.util.Date;

/**
 *
 * @author tokio
 */
public class Rostro {

    private int id;

    private int eje_x;
    private int eje_y;
    private int ancho;
    private Date fecha_desde;
    private Date fecha_hasta;
    private boolean matched;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEje_x() {
        return eje_x;
    }

    public void setEje_x(int eje_x) {
        this.eje_x = eje_x;
    }

    public int getEje_y() {
        return eje_y;
    }

    public void setEje_y(int eje_y) {
        this.eje_y = eje_y;
    }

    public Date getFecha_hasta() {
        return fecha_hasta;
    }

    public void setFecha_hasta(Date fecha_hasta) {
        this.fecha_hasta = fecha_hasta;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public Date getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public long getDuracion() {
        return (fecha_hasta.getTime() - fecha_desde.getTime()) / 1000;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean match) {
        this.matched = match;
    }

    public boolean esRostro(int x, int y) {
        boolean esRostro = false;
        double desplazamiento = Math.sqrt(Math.pow(x - this.eje_x, 2) + Math.pow(y - this.eje_y, 2));
        if (desplazamiento < ancho * 0.2) {
            esRostro = true;
        }
        return esRostro;
    }
}

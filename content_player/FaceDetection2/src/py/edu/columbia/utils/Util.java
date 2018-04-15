/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.utils;

import java.util.Date;

/**
 *
 * @author tokio
 */
public final class Util {
    public static long diferenciaEnSegundos(Date fecha1, Date fecha2){
        System.out.println("Milesimas segundos "+(fecha1.getTime() - fecha2.getTime()));
        System.out.println("segundos "+(fecha1.getTime() - fecha2.getTime())/1000);
        return (fecha1.getTime() - fecha2.getTime()) / 1000;
    }
}

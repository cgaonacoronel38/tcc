package py.edu.columbia.tcc.common;

import py.edu.columbia.tcc.exception.GDMEJBException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que encapsula diferentes funciones utilizadas comunmente.
 */
public class Util {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
    
    private final static String AS400_CONFIG_PATH = "/QIBM/UserData/OS400/SQLLib/Function/config/";
    
    private static String JAR_NAME = "AdvaEJB.jar";

    public Util(){
    }

    /**
     * Retorna verdadero si corresponde al sistema operativo.
     * Toma la propiedad "os.name" de la JVM y lo compara con el parametro.
     *
     * @param checkOS Sigla del sistema operativo donde se está ejecutando la JVM.
     *                Este parametro es buscado dentro del valor que retorna la propiedad "os.name"
     *
     * @return Retorna true si el parametro corresponde.
     */
    public boolean isOS(String checkOS){
        String os = System.getProperty("os.name");
        boolean isOS = false;

        System.out.printf("Sistema operativo a chequear: %s , sistema operativo actual: %s\n", checkOS, os);
        if(os.toUpperCase().contains(checkOS.toUpperCase()) || os.substring(0, 3).toUpperCase().contains(checkOS.toUpperCase())) {
            isOS = true;
        }

        return isOS;
    }

    /**
     * Retorna la ruta completa de la carpeta de configuracion de la aplicación, se incluye la barra al final del path.<p>
     *
     * <li>Ejemplo para AS400<br>
     *  /QIBM/UserData/OS400/SQLLib/Function/config/
     * 
     * <li>Ejemplo para Windows<br>
     * c:\\user\\alcides.alarcon\\documentos\\NetBeansProjects\\Ctt-Replication\\config\\
     *
     * @param db2ExternalFunction Indica si se esta trabajando con funciones externas desde db2
     * 
     * @return Texto que corresponde a la ruta donde se encuentra el jar.
     * @throws com.documenta.util.exception.FadvaEJBException
     */
    public String getApplicationConfigPath(boolean db2ExternalFunction) throws GDMEJBException {
        String appPath = null;
                
        //if(isOS("WINDOWS") || !db2ExternalFunction) {
        if(!db2ExternalFunction) {
            String fileSeparator = System.getProperty("file.separator");
            String userDir = System.getProperty("user.dir");
            
            LOGGER.info("Creando ruta del archivo de configuración, separador: {}, user.dir: {}", fileSeparator, userDir);
            System.out.printf("Creando ruta del archivo de configuración, separador: %s, user.dir: %s\n", fileSeparator, userDir);
            
            //XXX Cambiar
            if(isOS("linux")) {
                userDir = "/home/wildfly";
            } else if(userDir == null || userDir.trim().isEmpty() || userDir.trim().equals("/") || userDir.trim().equals("\\")) {
                userDir = getPathForJar();
                
                LOGGER.info("No se obtuvo ningún valor de la propiedad de sistema [user.dir], path obtenido desde el jar: {}", userDir);
                System.out.printf("No se obtuvo ningún valor de la propiedad de sistema [user.dir], path obtenido desde el jar: $s\n", userDir);
            }
            
            userDir = userDir.concat(userDir.endsWith(fileSeparator) ? "" : fileSeparator);
            
            appPath = String.format("%sconfig%s", userDir, fileSeparator);
        } else if(isOS("400") && db2ExternalFunction) {
            appPath = AS400_CONFIG_PATH;
        } else {
            throw new GDMEJBException("No se puede obtener la ruta para el archivo de configuración.");
        }
        
        return appPath;
    }
    
    private String getPathForJar() throws GDMEJBException {
        //XXX Desde el comando RUNJVA del as400 falla esta propiedad.
        //String path = System.getProperty("user.dir");
        
        URL url = Util.class.getResource("");
        
        LOGGER.info("Ruta de ejecución: {}", url.getPath());
        System.out.printf("Ruta de ejecución: %s\n", url.getPath());


        //======================================
        // ( 1 )  SI SE EJECUTA DESDE EL JAR
        //======================================
        String protocolo = url.getProtocol().toLowerCase() + ":/";
        //int posProtocolo = url.getPath().toLowerCase().indexOf(protocolo);
        int posJarName = url.getPath().toLowerCase().indexOf(JAR_NAME.toLowerCase());

        if(posJarName > 0){
            return url.getPath().substring(protocolo.length(), posJarName);
        } else {
            throw new GDMEJBException("No se pudo obtener la ruta relativa de ejecución de la aplicacíón.");
        }
    }

    public static void main(String[] args) throws GDMEJBException{
        Util util = new Util();
        System.out.println(util.getApplicationConfigPath(false));
    }
    
    public static boolean compareHours(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        int hourSystem, minuteSystem;

        hourSystem = calendar.get(Calendar.HOUR_OF_DAY);
        minuteSystem = calendar.get(Calendar.MINUTE);
        if (hourSystem == hour && minuteSystem == minute) {
            return true;
        } else {

            return false;
        }
    }
    
    /**
     * Retorna la diferencia en días entre dos fechas
     * @param higherDate
     * @param minorDate
     * @return 
     */
    public static int daysBetween(Date higherDate, Date minorDate) {
        long diferencia = higherDate.getTime() - minorDate.getTime();
        long dias = diferencia / (1000 * 60 * 60 * 24);
        if(dias < 0) dias = dias * -1;
        return (int) dias;
    }
}
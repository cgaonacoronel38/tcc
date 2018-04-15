package py.edu.columbia.tcc.login.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import py.edu.columbia.tcc.login.common.Util;
import py.edu.columbia.tcc.login.setup.AppSetup;


/**
 * Singleton que se encarga de leer los diferentes archivos properties,
 * luego crear los objetos setup y cargar los datos obtenidos
 * de los archivos.<br>
 * 
 * Los archivos properties están agrupados según la funcionalidad/utilización,
 * donde cada funcionalidad esta representado en una clase bean con el
 * sufijo setup para ser fácilmente reconocido.<br>
 *
 * @see clases setup en: com.pmc.docuemnta.setup
 */
public class ResourceManager {

    private Util util = new Util();
    private final static String PMC_PROPS = "adva-general-config.properties";
    
    // Para concatener con el nombre de los archivos properties
    private String appPathConfig = null;

    // Archivo principal de configuración, se cierra luego del load().
    private Properties propsPMC = null;
    private InputStream inputStreamPMC = null;      // Utilizado en ambiente windows y As400.
    private static String hostAS400 = "localhost";
    private boolean loaded = false;

    //Clases que agrupan las configuraciones
    
    private AppSetup appSetup;
    /*
    private MailUserConfirmSetup mailUserConfirmSetup;
    private MailForgotPasswordSetup mailForgotPasswordSetup;
    */
    
    private long firstLoadTime;
    
    private static ResourceManager resourceConfig;
    
    private ResourceManager() throws Exception, Exception {
        util = new Util();
        appPathConfig = util.getApplicationConfigPath(false);
        
        openProperties();
        load();
        
        //new CheckUpdateFile().start();
    }

    private void openProperties() throws Exception {
        try {
            if(inputStreamPMC != null) inputStreamPMC.close();
            inputStreamPMC = getInputStreamConfig(appPathConfig + PMC_PROPS);

            if (inputStreamPMC != null) {
                propsPMC = new Properties();
                propsPMC.load(inputStreamPMC);
            } else {
                throw new Exception("El archivo es nulo, no se pudo abrir el archivo de propiedades: " + PMC_PROPS);
            }
        } catch (IOException ex) {
            throw new Exception("Error al intentar abrir el archivo de propiedades: " + PMC_PROPS, ex);
        }
    }
    
    private void closeProperties() throws Exception {
        try {
            if (inputStreamPMC != null) {
                inputStreamPMC.close();
            }

            inputStreamPMC = null;
            propsPMC = null;
        } catch (Exception ex) {
            throw new Exception("Error al cerrar el archivo de propiedades.", ex);
        }
    }

    /**
     * Retorna el stream para escritura en el archivo de propiedades según el sistema operativo.
     *
     * @param file - Recibe la ruta completa del archivo de propiedades
     *
     * @return OutputStream - stream del archivo de propiedades para configuracion.
     */
    private OutputStream getOutputStreamConfig(String file) throws Exception {
        //FileInputStream fis = null;
        //IFSFileInputStream IFSfis = null;
        OutputStream os = null;

        try {
            if (util.isOS("AS400") || util.isOS("OS400")) {
                // Crea un objeto AS400 para el servidor que contiene los archivos.
                //AS400 as400 = new AS400(hostAS400);

                //os = new IFSFileOutputStream(as400, file, IFSFileOutputStream.SHARE_NONE);
            } else {
                os = new FileOutputStream(file);
            }
        //} catch (AS400SecurityException ex) {
        //    throw new PMCConfigException("Error de seguridad de as400.", ex);
        } catch (Exception ex) {
            throw new Exception("Error de I/O.", ex);
        }

        return os;
    }


    /**
     * Retorna el stream del archivo de propiedades según el sistema operativo.
     *
     * @param file - Recibe la ruta completa del archivo de propiedades
     *
     * @return InputStream - stream del archivo de propiedades para configuracion.
     */
    private InputStream getInputStreamConfig(String file) throws Exception {
        //FileInputStream fis = null;
        //IFSFileInputStream IFSfis = null;
        InputStream is = null;

        try {
            if (util.isOS("SYSTEM") || util.isOS("400")) {
                // Crea un objeto AS400 para el servidor que contiene los archivos.
                //AS400 as400 = new AS400(hostAS400);

                //is = new IFSFileInputStream(as400, file, IFSFileInputStream.SHARE_ALL);
                System.out.println("Archivo de configuración del servicio en AS400: " + file + is.available());
            } else {
                is = new FileInputStream(file);
                System.out.println("Archivo de configuración del servicio en Windows: " + file + is.available());                
            }

            return is;            
        } catch (Exception ex) {
            throw new Exception("Error en búsqueda de archivo de configuración.", ex);
        }
    }  
        
    /**
     * Carga propiedades que son leidas en la replicación indicando configuraciones generales
     * del servicio.
     */
    private void loadAppSetup() throws Exception {
        
        appSetup = new AppSetup();
        
        appSetup.setPuMaster(getProp(propsPMC, "app.persistence.master", PMC_PROPS));
        appSetup.setPuSlave(getProp(propsPMC, "app.persistence.slave", PMC_PROPS));
        
        /*
        appSetup.setUrlIndex(getProp(propsPMC, "app.urlIndex", PMC_PROPS));
        appSetup.setDomain(getProp(propsPMC, "app.domain", PMC_PROPS));
        appSetup.setPersistenceUnitName(getProp(propsPMC, "app.persistenceUnitName", PMC_PROPS));
        appSetup.setPassPhrase(getProp(propsPMC, "app.passPhrase", PMC_PROPS));
        appSetup.setLoginFailedAttempts(Integer.parseInt(getProp(propsPMC, "app.loginFailedAttempts", PMC_PROPS)));
        appSetup.setLoginHoursBloqued(Integer.parseInt(getProp(propsPMC, "app.loginHoursBloqued", PMC_PROPS)));
        appSetup.setDefaultRealmGroupName(getProp(propsPMC, "app.defaultRealmGroupName", PMC_PROPS));
        */
    }
    
    /**
     * Carga propiedades que son leidas en la replicación indicando configuraciones generales
     * del servicio.
     */
    private void loadMailSetup() throws Exception {
        /*
        mailUserConfirmSetup = new MailUserConfirmSetup();
        
        mailUserConfirmSetup.setSubjectActivation(getProp(propsPMC, "mail.userConfirm.subjectActivation", PMC_PROPS));        
        mailUserConfirmSetup.setSubject(getProp(propsPMC, "mail.userConfirm.subject", PMC_PROPS));
        mailUserConfirmSetup.setFrom(getProp(propsPMC, "mail.userConfirm.from", PMC_PROPS));
        mailUserConfirmSetup.setServerHost(getProp(propsPMC, "mail.userConfirm.serverIp", PMC_PROPS));
        mailUserConfirmSetup.setServerPort(getProp(propsPMC, "mail.userConfirm.serverPort", PMC_PROPS));
        mailUserConfirmSetup.setUserName(getProp(propsPMC, "mail.userConfirm.userName", PMC_PROPS));
        mailUserConfirmSetup.setPassword(getProp(propsPMC, "mail.userConfirm.password", PMC_PROPS));
        mailUserConfirmSetup.setAttachFile(getProp(propsPMC, "mail.userConfirm.attachFile", PMC_PROPS));
        mailUserConfirmSetup.setContextLink(getProp(propsPMC, "mail.userConfirm.contextLink", PMC_PROPS));
        mailUserConfirmSetup.setSeparatorLinkField(getProp(propsPMC, "mail.userConfirm.separatorLinkField", PMC_PROPS));
        mailUserConfirmSetup.setValueToMultiplyIdUser(Integer.parseInt(getProp(propsPMC, "mail.userConfirm.valueToMultiplyIdUser", PMC_PROPS)));        
        mailUserConfirmSetup.setLinkHoursValidity(Integer.parseInt(getProp(propsPMC, "mail.userConfirm.linkHoursValidity", PMC_PROPS)));
        */
    }
    
    /**
     * Carga propiedades que son leidas en la replicación indicando configuraciones generales
     * del servicio.
     */
    private void loadMailForgotPasswordSetup() throws Exception {
        /*
        mailForgotPasswordSetup = new MailForgotPasswordSetup();
        
        mailForgotPasswordSetup.setSubjectActivation(getProp(propsPMC, "mail.forgotPassword.subjectActivation", PMC_PROPS));        
        mailForgotPasswordSetup.setSubject(getProp(propsPMC, "mail.forgotPassword.subject", PMC_PROPS));
        mailForgotPasswordSetup.setFrom(getProp(propsPMC, "mail.forgotPassword.from", PMC_PROPS));
        mailForgotPasswordSetup.setServerHost(getProp(propsPMC, "mail.forgotPassword.serverIp", PMC_PROPS));
        mailForgotPasswordSetup.setServerPort(getProp(propsPMC, "mail.forgotPassword.serverPort", PMC_PROPS));
        mailForgotPasswordSetup.setUserName(getProp(propsPMC, "mail.forgotPassword.userName", PMC_PROPS));
        mailForgotPasswordSetup.setPassword(getProp(propsPMC, "mail.forgotPassword.password", PMC_PROPS));
        mailForgotPasswordSetup.setAttachFile(getProp(propsPMC, "mail.forgotPassword.attachFile", PMC_PROPS));
        */
    }    
    
    /**
     * Carga las propiedades que serán utilizadas por la app.
     */
    private void load() throws Exception {
        if (!loaded) {
            loadAppSetup();
            loadMailSetup();
            loadMailForgotPasswordSetup();
            
            closeProperties();
        }
    }

    private String getProp(Properties p, String key, String nameProp) throws Exception {
        String value = p.getProperty(key);

        //XXX 1.6 trim() borra enter + avance de linea
        if (value == null || value.isEmpty()) {
            throw new Exception(String.format("El archivo de propiedades (%s) no contiene la clave: ", nameProp) + key);
        }

        //aa 23/06/2011
        //El trim() fallo, eliminó los caracteres \r\n seteados en el archivo de dss.propiedades,
        //como fin de msg. de interface, esto paso después de una actualizaciónd de java.
        //XXX Preguntar por valores ascii charAt() o algo parecido.
        //return value.trim();
        return value.endsWith("\r") || value.endsWith("\n") ? value : value.trim();
    }

    /**
     * Escribe un valor según su clave en el archivo de propiedades principal.
     * 
     * @param key Clave a actualizar.
     * @param value valor a guardarse en la clave.
     */
    private void setPropsDSS(String key, String value) throws Exception {
        OutputStream os = null;
        Properties outProps = null;
        openProperties();
        propsPMC.setProperty(key, value);

        try {
            os = getOutputStreamConfig(appPathConfig + PMC_PROPS);
            propsPMC.store(os, null);
            os.flush();
        } catch (Exception ex) {
            throw new Exception("Error al setear una propiedad.", ex);
        } finally {
            try {
                if(os != null) os.close();
                os = null;
                outProps = null;
            } catch (Exception ex) {
                //DB2InsertLog.saveLog(ex, "", "", "Error al cerrar el archivo de propiedades.", "CONFIG");
            }
        }
    }

    public AppSetup getAppSetup() {
        return appSetup;
    }

    public void setAppSetup(AppSetup generalSetup) {
        this.appSetup = generalSetup;
    }

    /*
    public MailUserConfirmSetup getMailUserConfirmSetup() {
        return mailUserConfirmSetup;
    }

    public void setMailUserConfirmSetup(MailUserConfirmSetup mailUserConfirmSetup) {
        this.mailUserConfirmSetup = mailUserConfirmSetup;
    }

    public MailForgotPasswordSetup getMailForgotPasswordSetup() {
        return mailForgotPasswordSetup;
    }

    public void setMailForgotPasswordSetup(MailForgotPasswordSetup mailForgotPasswordSetup) {
        this.mailForgotPasswordSetup = mailForgotPasswordSetup;
    }
    */
    
    public static ResourceManager getInstance() throws Exception, Exception {
        if(resourceConfig == null) {
            resourceConfig = new ResourceManager();
        }
        
        return resourceConfig;
    }
    
    public static void main(String[] args) throws Exception, Exception {
        
        System.out.println(ResourceManager.getInstance().getAppSetup().toString());
        
        /*
        System.out.println(ResourceManager.getInstance().getMailUserConfirmSetup().toString());        
        */
    }
    
    
    class CheckUpdateFile extends Thread {
        private java.io.File file;
        
        private CheckUpdateFile() {
            init();
        }

        private void init() {
            file = new File(appPathConfig + PMC_PROPS);
            
            if(file.exists()) {
                firstLoadTime = file.lastModified();
            }   
            
            file = null;
        }
        
        @Override
        public void run() {
            try {
                while (true) {
                    file = new File(appPathConfig + PMC_PROPS);
                    
                    if (file.exists()) {
                        long currentLoad = file.lastModified();
                        
                        if (firstLoadTime != currentLoad) {
                            firstLoadTime = currentLoad;
                            
                            openProperties();
                            load();
                            System.out.printf("Se re-cargaron las propiedades al detectarse cambios en el archivo [%s].\n", appPathConfig + PMC_PROPS);                            
                        }
                    } else {
                        System.out.printf("El archivo de configuración [%s] no existe.\n", appPathConfig + PMC_PROPS);
                    }                    
                    
                    file = null;
                    Thread.sleep(120000);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void interrupt() {
            System.out.println("Se interrumpió el hilo que chequea el archivo de propiedades."); 
        }
    }
}
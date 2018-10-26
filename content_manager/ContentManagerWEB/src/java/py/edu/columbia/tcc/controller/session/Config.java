package py.edu.columbia.tcc.controller.session;

import py.edu.columbia.tcc.exception.GDMEJBException;
import java.io.Serializable;
import java.util.TimeZone;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.columbia.tcc.common.Params;
import py.edu.columbia.tcc.login.ejb.service.LoginMaps;

/**
 *
 * @author tid
 */
@javax.inject.Named(value = "config")
@javax.enterprise.context.SessionScoped
public class Config implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(Config.class);
    
    @Inject
    private LoginMaps dcorsMaps;
    
    @Inject
    private GDMSession gdmSession;
    
    
    public String appTitle() throws GDMEJBException {
        try {
            int idcompany = gdmSession.getDefaultCompany().getIdCompany();
            return dcorsMaps.findSysParamByKey(Params.APP_TITLE).getValue();
        } catch (Exception ex) {
            if (ex instanceof GDMEJBException) {
                throw (GDMEJBException) ex;
            }

            throw new GDMEJBException("No se pudo obtener el título principal de la aplicación.", ex);
        }
    }
    
    public String appJSFSufix() throws GDMEJBException {
        try {
            int idcompany = gdmSession.getDefaultCompany().getIdCompany();
//            log.info("Comapny: "+idcompany);
            return dcorsMaps.findSysParamByKey(Params.APP_SUFIX).getValue();
        } catch (Exception ex) {
            if (ex instanceof GDMEJBException) {
                throw (GDMEJBException) ex;
            }

            throw new GDMEJBException("No se pudo obtener el sufijo URL del sub-sistema.", ex);
        }
    }
    
    public boolean isAppPlatformMaster() throws GDMEJBException {
        try {
            int idcompany = gdmSession.getDefaultCompany().getIdCompany();
            return dcorsMaps.findSysParamByKey(Params.APP_MASTER).getValue().equalsIgnoreCase("true");
        } catch (Exception ex) {
            if (ex instanceof GDMEJBException) {
                throw (GDMEJBException) ex;
            }

            throw new GDMEJBException("No se pudo obtener el indicar de tipo de plataforma (Master/Esclavo).", ex);
        }
    }
    
    public TimeZone timeZoneDefault() {
        return TimeZone.getDefault();
    }
    
    public String pathTmpFile() throws GDMEJBException {
        try {
            String path = null;
            String separator = null;
            int idcompany = gdmSession.getDefaultCompany().getIdCompany();

            String os = System.getProperty("os.name");
            separator = System.getProperty("file.separator");

            log.info("Verificando ruta de archivos temporales: " + os + " - " + separator);

            path = dcorsMaps.findSysParamByKey(Params.APP_PATH_TMPFILE).getValue();
            if (!path.endsWith("/") && !path.endsWith("\\")) {
                if (os.toLowerCase().contains("linux")) {
                    path = path.concat("/");
                } else {
                    path = path.concat("\\");
                }
            }

            return path;
        } catch (Exception ex) {
            if (ex instanceof GDMEJBException) {
                throw (GDMEJBException) ex;
            }

            throw new GDMEJBException("No se pudo obtener la ruta temporal para UP/DOWN de archivos.", ex);
        }
    }
    
    public String appAlertRecipients() throws GDMEJBException {
        try {
            int idcompany = gdmSession.getDefaultCompany().getIdCompany();
            return dcorsMaps.findSysParamByKey(Params.APP_ALERT_RECIPIENTS).getValue();
        } catch (Exception ex) {
            if (ex instanceof GDMEJBException) {
                throw (GDMEJBException) ex;
            }

            throw new GDMEJBException("No se pudo obtener lista de destinatarios SOPORTE para envío de alertas vía e-mail.", ex);
        }
    }
    
    public String appAlertTIDRecipients() throws GDMEJBException {
        try {
        int idcompany = gdmSession.getDefaultCompany().getIdCompany();  
        return dcorsMaps.findSysParamByKey(Params.APP_ALERT_TID_RECIPIENTS).getValue();
        } catch (Exception ex) {
            if (ex instanceof GDMEJBException) {
                throw (GDMEJBException) ex;
            }

            throw new GDMEJBException("No se pudo obtener lista de destinatarios TID para envío de alertas vía e-mail.", ex);
        }
    }    
}

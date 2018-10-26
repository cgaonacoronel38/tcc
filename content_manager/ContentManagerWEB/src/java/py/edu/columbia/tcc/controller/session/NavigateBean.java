/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package py.edu.columbia.tcc.controller.session;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tid
 */
@javax.inject.Named(value = "nav")
@javax.enterprise.context.SessionScoped
public class NavigateBean implements Serializable{
    private static final Logger log = LoggerFactory.getLogger(NavigateBean.class);
    
    private String contextPath;
    
    private String realPath;
    
    private final boolean defaultFacesRedirect =  false;
    
    private final static String FACES_REDIRECT_PARAM = "?faces-redirect=true";
    
    private final static String NAV_PATH = "%s/%s.%s%s";
    
    private final static String NAV_PATH_RESTRICTED  = "%s/restricted/%s.%s%s";
   
    private String jsfSuffix;
    
    private String currentTitle;
        
    @Inject
    private Config config;
    
    @PostConstruct
    public void init() {
        try {
            contextPath = ((HttpServletRequest) FacesContext.getCurrentInstance()
                            .getExternalContext().getRequest()).getContextPath();
        
            realPath = ((HttpServletRequest) FacesContext.getCurrentInstance()
                            .getExternalContext().getRequest()).getRequestURL().toString();
            
            jsfSuffix = config.appJSFSufix();
        } catch (Exception ex) {
            log.error("No se pudo inicializar correctamente el NavigateBean.", ex.getLocalizedMessage());           
            jsfSuffix = "tcc";
        }
    }
    
    /**
     * Ruta real de la aplicación
     * 
     * @return 
     */
    public String getRealPath() {
        return realPath;
    }
    
    /**
     * Contexto de ejecución de la aplicación se utiliza para abrir URL.
     * 
     * @return 
     */
    public String getContextPath() {
        return contextPath;
    }
    
    public String getHome() {
        return contextPath;
    }
    
    public String getCurrentTitle() {
        return currentTitle;
    }
    
    /**
     * Home de la aplicación
     * 
     * @return 
     */
    public String getIndex() {
        return getIndex(defaultFacesRedirect);
    }
    public String getIndex(boolean facesRedirect) {
        return String.format(NAV_PATH, contextPath, "index", jsfSuffix, (facesRedirect ? FACES_REDIRECT_PARAM : ""));
    }
    
    
    /**
     * Página para solicitar cambio de contraseña.
     * 
     * @return 
     */
    public String getForgotPassword() {
        return getForgotPassword(defaultFacesRedirect);
    }
    public String getForgotPassword(boolean facesRedirect) {
        return String.format(NAV_PATH, contextPath, "auth/forgotPassword", jsfSuffix, (facesRedirect ? FACES_REDIRECT_PARAM : ""));
    }
    
    /**
     * Página-restringida para cambio de contraseña
     * 
     * @return 
     */
    public String getChangePassword() {
        return getChangePassword(defaultFacesRedirect);
    }
    public String getChangePassword(boolean facesRedirect) {
        return String.format(NAV_PATH_RESTRICTED, contextPath, "auth/changePassword", jsfSuffix, (facesRedirect ? FACES_REDIRECT_PARAM : ""));
    }
    
    
    /**
     * Path del servlet para finalizar una sesión.
     * 
     * @return 
     */
    public String getLogout() {
        return contextPath + "/auth/logout";
    }

    
    /**
     * Página de Login.
     * 
     * @return 
     */
    public String getLogin() {
        return getLogin(defaultFacesRedirect);
    }
    public String getLogin(boolean facesRedirect) {
        return String.format(NAV_PATH, contextPath, "auth/login", jsfSuffix, (facesRedirect ? FACES_REDIRECT_PARAM : ""));
    }

    
    /**
     * Página de error de logín.
     * 
     * @return 
     */
    public String getLoginError() {
        return getLoginError(defaultFacesRedirect);
    }
    public String getLoginError(boolean facesRedirect) {
        return String.format(NAV_PATH, contextPath, "auth/loginError", jsfSuffix, (facesRedirect ? FACES_REDIRECT_PARAM : ""));
    }
    
    
    /**
     * Página-restringida inicial luego de logearse correctamente.
     * 
     * @return 
     */
    public String getUserIndex() {
        return getUserIndex(defaultFacesRedirect);
    }
    public String getUserIndex(boolean facesRedirect) {
        return String.format(NAV_PATH_RESTRICTED, contextPath, "indexUser", jsfSuffix, (facesRedirect ? FACES_REDIRECT_PARAM : ""));
    }

    public String getUser() {
        return getUserIndex(defaultFacesRedirect);
    }

    public String getUser(boolean facesRedirect) {
        return String.format(NAV_PATH_RESTRICTED, contextPath, "indexUser", jsfSuffix,"");
    }

    public String getMenu() {
        return getUserIndex(defaultFacesRedirect);
    }

    public String getMenu(boolean facesRedirect) {
        return String.format(NAV_PATH_RESTRICTED, contextPath, "billing/createInvoice", jsfSuffix,"");
    }
}

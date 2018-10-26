/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package py.edu.columbia.tcc.controller.session;

import py.edu.columbia.tcc.login.ejb.service.LoginMaps;
import py.edu.columbia.tcc.login.model.Company;
import py.edu.columbia.tcc.login.model.User;
import py.edu.columbia.tcc.exception.GDMEJBException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CDI + JSF 2.2 => se puede utilizar en cualquier otro componente.
 * 
 * @author cgaona
 */
@javax.inject.Named(value = "gdmSession")
@javax.enterprise.context.SessionScoped
public class GDMSession implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(GDMSession.class);
    
    @Inject
    private LoginMaps dcorsMaps;
    
    private User user;
    private Company company;
    private String previousURL;
    
    
    //private Profile defaultProfile;
    
    public void removeUserInHttpSession() {
        FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().remove("user");
        
        log.info("Se removió el usuario del objeto HttpSession.");
    }
    
    public void addUserInHttpSession(User su) {
        FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap()
                    .put("user", su.getUserName());        
        
        log.info("Se cargo el usuario en el objeto HttpSession.");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    /*
    public Profile getDefaultProfile() throws PMCConfigException {
        if(sysuser != null && defaultProfile == null) {
            for(Profile p : sysuser.getProfileList()) {
                if(p.isProfiledefault()) {
                    defaultProfile = p;
                    break;
                }
            }
            
            if(defaultProfile == null) {
                throw new PMCConfigException("El usuario no tiene configurado una cuenta por defecto.");
            }
        }
        
        return defaultProfile;
    }
    
    public List<Profile> getProfiles() {
        if(sysuser != null) {
            return sysuser.getProfileList();
        }
        
        return new ArrayList();
    }
    
    public Profile findProfile(int idprofile) {
        return findProfile(idprofile, false);
    }
    public Profile findProfile(int idprofile, boolean profileDefault) {
        if(sysuser != null) {
            for(Profile p : sysuser.getProfileList()) {
                if(p.getProfilePK().getIdprofile() == idprofile) {
                    if(profileDefault) p.setProfiledefault(true);
                    
                    return p;
                }
            }
        }
        
        return null;
    }
    */
    
    public Company getDefaultCompany() throws GDMEJBException {
        try {
            if(user != null){
//                log.info("Obteniendo compañia de usuario");
                company = user.getIdCompany();
            } else {
               
            }
            return company;
        } catch (Exception ex) {
            if(ex instanceof GDMEJBException) throw (GDMEJBException) ex;
            
            throw new GDMEJBException("No se pudo obtener la Empresa por defecto.", ex);
        }
    }
    
    /**
     * Retorna TRUE si hay un usuario logueado en el sistema.
     * 
     * @return 
     */
    public boolean isSignIn() {
        FacesContext fc = FacesContext.getCurrentInstance();
        
        if(fc == null) {
            log.warn("FacesContext nulo no se puede chequear la sesion.");
            
            return false;
        }
        
        return (fc.getExternalContext().getUserPrincipal() != null);
    }
    
    
    public boolean isSignIn(HttpServletRequest request) {
        FacesContext fc = FacesContext.getCurrentInstance();
        
        if(fc == null) {
//            log.warn("FacesContext nulo no se puede chequear la sesion desde JSF.");
            
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                return false;
            } else {
                String user = session.getAttribute("user").toString();
                
                if(!user.trim().isEmpty()) {
//                    log.info("Usuario logueado, info desde la sesión, username: {}", user);
                
                    return true;
                } else {
//                    log.info("Usuario logueado pero vacio, info desde la sessión.");
                    
                    return false;
                }
            }
        }
        
        return (fc.getExternalContext().getUserPrincipal() != null);        
        
    }
    
    public boolean isRole(String role) {
        HttpServletRequest r = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return r.isUserInRole(role);
    }

    public String getPreviousURL() {
        return previousURL;
    }

    public void setPreviousURL(String previousURL) {
        this.previousURL = previousURL;
    }
    
    public String getHolaMundo() {
        return "Hola mundo";
    }
}
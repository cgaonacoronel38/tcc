/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.controller.view.auth;

import py.edu.columbia.tcc.common.MsgUtil;
import py.edu.columbia.tcc.controller.session.GDMSession;
import py.edu.columbia.tcc.login.ejb.jpa.UserFacade;
import py.edu.columbia.tcc.login.model.User;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tid
 */
@ManagedBean(name = "changePasswordMB")
@ViewScoped
public class ChangePassword {

    private static final Logger log = LoggerFactory.getLogger(ChangePassword.class);

    @Inject
    private GDMSession sessionEJB;

    @Inject
    private UserFacade sysuserEJB;

    private String usernameToChange;

    private String currPassword;

    private String newPassword;

    private String confirmPassword;

    public String getCurrPassword() {
        return currPassword;
    }

    public void setCurrPassword(String currPassword) {
        this.currPassword = currPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsernameToChange() {
        return usernameToChange;
    }

    public void setUsernameToChange(String usernameToChange) {
        this.usernameToChange = usernameToChange;
    }

    public void changePWD() {
        User su = null;

        try {
            su = sessionEJB.getUser();

            if (su == null) {
                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_FATAL,
                        "Atención!",
                        "El usuario logueado no es válido, imposible cambiar contraseña.");

                return;
            }

            // Cambiar contraseña desde el mismo usuario.
            if (usernameToChange == null || usernameToChange.trim().isEmpty()) {
                usernameToChange = su.getUserName();
            }
            
            if(!confirmPassword.equals(newPassword)){
                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                        "Atención!",
                        "Las contraseñas no coinciden!!!");
                return;
            }
            /*else { // Cambiar contraseña desde otro usuario.
                if(sessionEJB.isRole("admin")) {
                    MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN,
                                                 "Error",
                                                 "Ud. no es un Administrador, no podrá realizar el cambio de contraseña.");
                    
                    return;
                }
            }*/
            if (!sysuserEJB.validPassword(usernameToChange, currPassword)) {
                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                        "Error",
                        "La contraseña actual no es válida.");
                return;
            }

            sysuserEJB.changePassword(su, usernameToChange, newPassword);

            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO,
                    "Contraseña cambiada exitosamente!",
                    " ");
        } catch (Exception ex) {
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No se pudo cambiar la contraseña, consulta con el area de Soporte.");

            log.error("Error al intentar cambiar la contraseña del usuario " + (su == null ? "null" : su.getUserName()), ex);
        }
    }
}

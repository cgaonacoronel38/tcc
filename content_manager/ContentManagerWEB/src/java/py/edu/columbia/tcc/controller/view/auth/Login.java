package py.edu.columbia.tcc.controller.view.auth;

import py.edu.columbia.tcc.common.MsgUtil;
import py.edu.columbia.tcc.controller.session.GDMSession;
import py.edu.columbia.tcc.controller.session.Config;
import py.edu.columbia.tcc.controller.session.NavigateBean;
import py.edu.columbia.tcc.login.ejb.jpa.UserFacade;
import py.edu.columbia.tcc.login.model.User;
import java.io.Serializable;
import java.security.Principal;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tokio
 */
@ManagedBean(name = "loginMB")
@ViewScoped
public class Login implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(Login.class);

    @Inject
    private Config config;

    @Inject
    private UserFacade sysuserEJB;

    @Inject
    private NavigateBean nav;

    @Inject
    private GDMSession gdmSession;

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    private boolean valid() {
        boolean ok = true;

        if (username == null || username.isEmpty()) {
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "El usuario es un valor requerido.");

            ok = false;
        }

        if (password == null || password.isEmpty()) {
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "La contraseña es un valor requerido.");

            ok = false;
        }

        return ok;
    }

    public void signIn(ActionEvent ae) {
        log.info("validando usuario: " + username + "; " + password);
        if (!valid()) {
            return;
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) ec.getRequest();
        HttpServletResponse res = (HttpServletResponse) ec.getResponse();

        try {
            User user = gdmSession.getUser();
            if (user != null && user.getActive() == true) {
                ec.redirect(nav.getUserIndex() + "?status=0");
            }

            User su = sysuserEJB.findByUsername(username);
            log.info("usuario" + username);
            if (su == null) {
                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                        "ERROR",
                        "Usuario no válido.");

                return;
            }

            if (su.getActive()) {
                log.info("El usuario está activo, username: {}, activo: {}", username, su.getActive());

                try {
                    log.info(username + " ; " + password);
                    req.login(username, password);
                } catch (Exception ex) {
                    log.info(ex.getLocalizedMessage());
                    log.error("Error en inicio de sesión (request.login()) para el usuario: {}", username, ex.getCause());
                    System.err.println("Error" + ex.getMessage());

                    StringBuilder msg = new StringBuilder();
                    msg.append("Usuario o contraseña no válido!!.  Recuerde que solo tiene [ 3 ] intentos, ");
                    msg.append("de lo contrario la cuenta se bloquea por [ 24 ] horas.");

                    MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                            "ERROR",
                            msg.toString());

                    return;
                }
            } else {
                log.info("El usuario está bloquedao, username: {}, activo: {}", username, su.getActive());

                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                        "ERROR",
                        "Usuario bloqueado.");

                return;
            }

            if (req.getUserPrincipal() != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(req.isUserInRole("admin") ? "[ADMIN]" : "");
                sb.append(req.isUserInRole("super") ? "[SUPER]" : "");
                sb.append(req.isUserInRole("user") ? "[USER]" : "");

                log.info("Usuario autenticado correctamente: {}, roles asignados: {}", username, sb.toString());
            }

            sysuserEJB.updateLastSignInNATIVE(su.getIdCompany().getIdCompany(),
                    su.getIdUser());
            sysuserEJB.detached(su);

            gdmSession.setUser(su);

            //Para utilizarlo desde el WebFilter
            gdmSession.addUserInHttpSession(su);

            log.info("Redireccionando a {}, gdmSession: {}", nav.getUserIndex() + "?status=0", gdmSession.toString());
            //res.setContentType("text/xml");
            //res.getWriter()
            //    .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            //    .printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>", result);            

            ec.redirect(nav.getUserIndex() + "?status=0");
        } catch (Exception ex) {
            log.error("Error en autenticación de usuario: " + username, ex);

            try {
                res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (Exception ex1) {
                log.error("No se pudo crear el error 500 desde el login.", ex);
            }
        }
    }

    /*
    public void signIn(ActionEvent ae) {
        if (!valid()) {
            return;
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) ec.getRequest();
        HttpServletResponse res = (HttpServletResponse) ec.getResponse();

        try {
            User su = sysuserEJB.findByUsername(username);
            if (su == null) {
                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                        "ERROR",
                        "Usuario no válido.");

                return;
            }

            switch (su.getUserstatus()) {
                case ACTIVE:
                    log.info("El usuario está activo, username: {}, estado: {}", username, su.getUserstatus().name());

                    try {
                        req.login(username, password);
                    } catch (Exception ex) {
                        log.error("Error en inicio de sesión (request.login()) para el usuario: {}", username, ex.getCause());
                        System.err.println("Error" + ex.getMessage());

                        StringBuilder msg = new StringBuilder();
                        msg.append("Usuario o contraseña no válido!!.  Recuerde que solo tiene [ 3 ] intentos, ");
                        msg.append("de lo contrario la cuenta se bloquea por [ 24 ] horas.");

                        MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                                "ERROR",
                                msg.toString());

                        return;
                    }

                    break;
                case LOCKED:
                case FORGOT_PASSWORD:
                    log.info("El usuario está bloquedao, username: {}, estado: {}", username, su.getUserstatus().name());

                    MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_ERROR,
                            "ERROR",
                            "Usuario bloqueado.");

                    return;
            }

            if (req.getUserPrincipal() != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(req.isUserInRole("admin") ? "[ADMIN]" : "");
                sb.append(req.isUserInRole("super") ? "[SUPER]" : "");
                sb.append(req.isUserInRole("user") ? "[USER]" : "");

                log.info("Usuario autenticado correctamente: {}, roles asignados: {}", username, sb.toString());
            }

            sysuserEJB.updateLastSignInNATIVE(su.getUserPK().getIdcompany(),
                    su.getUserPK().getIdsysuser());
            sysuserEJB.detached(su);

            gdmSession.setSysuser(su);

            //Para utilizarlo desde el WebFilter
            gdmSession.addUserInHttpSession(su);

            log.info("Redireccionando a {}, gdmSession: {}", nav.getUserIndex() + "?status=0", gdmSession.toString());
            //res.setContentType("text/xml");
            //res.getWriter()
            //    .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            //    .printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>", result);            

            ec.redirect(nav.getUserIndex() + "?status=0");
        } catch (Exception ex) {
            log.error("Error en autenticación de usuario: " + username, ex);

            try {
                res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (Exception ex1) {
                log.error("No se pudo crear el error 500 desde el login.", ex);
            }
        }
    }
     */
    public void signOff() {
        HttpServletRequest request = null;

        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            StringBuilder sb = new StringBuilder();
            sb.append("Información del LOGOUT:\n");
            Principal p = request.getUserPrincipal();
            if (p != null) {
                sb.append(String.format(" -1 Usuario principal: %s\n", request.getUserPrincipal().getName()));
                sb.append(String.format("\t->Tiene el rol administrador: %s\n", request.isUserInRole("ADMIN")));
                sb.append(String.format("\t->Tiene el rol supervisor: %s\n", request.isUserInRole("SUPER")));
                sb.append(String.format("\t->Tiene el rol usuario común: %s\n\n", request.isUserInRole("USER")));
            } else {
                sb.append("->No hay usuario logueado.\n");
            }

            sb.append(" -2 Información de conexión/sesión:\n");
            HttpSession hs = request.getSession();
            if (hs != null) {
                sb.append(String.format("\t-> Id. de sessión: %s\n", request.getSession().getId()));
                sb.append(String.format("\t-> Usuario remoto: %s\n", request.getRemoteUser()));
                sb.append(String.format("\t-> Tipo autenticación: %s\n", request.getAuthType()));
                sb.append(String.format("\t-> Puerto remoto: %s\n", request.getRemotePort()));
                sb.append(String.format("\t-> Dirección remota: %s\n", request.getRemoteAddr()));
                sb.append(String.format("\t-> Host remoto: %s\n", request.getRemoteHost()));

                // Se invalida la actual session HTTP.
                // Se llama internamente la método de JAAS LoginModule logout()
                request.getSession().invalidate();
                gdmSession.setUser(null);
                gdmSession.removeUserInHttpSession();

                log.info(sb.toString());
            } else {
                sb.append("No se pudo obtener información de sesión, no se puede invalidar.");
            }

            // Se re-direcciona a la página de inicio, AREA-FREE
            request.getServletContext().log("Usuario ha sido INVALIDADO, redireccionando al index...");

            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO,
                    "Advertencia",
                    "Sesión finalizada!");

            FacesContext.getCurrentInstance().getExternalContext().redirect(nav.getHome());

            //response.sendRedirect(request.getContextPath() + "/index.pf?faces-redirect=true");
            //response.sendRedirect(nav.getHome());
        } catch (Exception ex) {
            log.error("Error al realizar el logout del usuario: " + username, ex);

            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_FATAL,
                    "ERROR",
                    "No se pudo realziar el cierre de sesión, consulte con Soporte.");
        }
    }
}

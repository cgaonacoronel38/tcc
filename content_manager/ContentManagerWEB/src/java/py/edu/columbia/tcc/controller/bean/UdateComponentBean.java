/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.controller.bean;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import py.edu.columbia.tcc.controller.view.utils.Message;

/**
 *
 * @author tokio
 */
@Stateless
public class UdateComponentBean {
    @Asynchronous
    public static void updateDashboard(FacesContext context) {
        try {
            System.out.println("actualizando dashboard");
//            context.getCurrentInstance().("@([id$=formDBView])");
            System.out.println("dashboard actualizado");
            Message.info("Registro exitoso", "El panel ha sido agregado exitosamente!, refrescando datos...");
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso", "El panel ha sido agregado exitosamente!, refrescando datos..."));
        } catch (Exception f) {
            System.err.println("Error al actuliazar level dashboard: " + f.getMessage());
        }
    }
}

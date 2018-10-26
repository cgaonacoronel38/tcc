/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package py.edu.columbia.tcc.ws;

//import py.edu.columbia.tcc.ejb.jdbc.content.AudienceJDBCFacade;
import py.edu.columbia.tcc.ejb.jdbc.content.DeviceContentJDBCFacade;
import py.edu.columbia.tcc.model.bean.AudienceBean;
import py.edu.columbia.tcc.model.bean.RemoteContentBean;
import java.io.File;
import java.io.Serializable;
import java.util.logging.Level;
import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.columbia.tcc.controller.session.GDMSession;
import py.edu.columbia.tcc.login.model.Company;

@Path("/audience")
@ManagedBean
public class WSAudience implements Serializable {

    private final Logger log = LoggerFactory.getLogger(WSAudience.class);
    
    @Inject
    private GDMSession sessionEJB;
    
    String filePath = null;
    String idContent = null;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(AudienceBean audience) {
        try {
//            AudienceJDBCFacade jdbc = new AudienceJDBCFacade();
//            jdbc.insertar(audience);
            return Response.status(201).entity("Audiencia registrada").build();
        } catch (Exception e) {
            log.info("Error al registrar audiencia: " + e.getLocalizedMessage());
            return Response.status(500).entity("Error al registrar audiencia").build();
        } 
    }
    
    @POST
    @Path("/notify")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response notify(RemoteContentBean remoteContent) {
        try {
            DeviceContentJDBCFacade contentEJB = new DeviceContentJDBCFacade();
            
            contentEJB.notifyDownload(remoteContent.getIdContent(), remoteContent.getIdRemoteContent(), remoteContent.getDateAdd());
            System.out.println("notificacion registrada");
            return Response.status(201).entity("Contenido registrado").build();
        } catch (Exception e) {
            log.info("Error al registrar recepciond de contenido " + e.getLocalizedMessage());
            return Response.status(500).entity("Error al registrar contenido").build();
        }
    }

    @GET
    @Path("/download")
    @Produces("application/zip")
    public Response downloadFile(@QueryParam("device") String device) {
        DeviceContentJDBCFacade deviceContentEJB = new DeviceContentJDBCFacade();
        String path = deviceContentEJB.getContentForDevice(device);
        
        File file = new File(path);
        if (file.exists()) {
            //deviceContentEJB.setProcessed(device, path);
            
            ResponseBuilder responseBuilder = Response.ok((Object) file);
            responseBuilder.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            return responseBuilder.build();
        } else {
            return Response.status(404).entity("Contenido no encontrado").build();
        }

    }
    
    @GET
    @Path("/prueba")
    @Produces(MediaType.APPLICATION_JSON)
    public Company prueba() {
        Company company =new Company();
        try {
            company.setIdCompany(1);
            company.setAddress("adrress");
            company.setEmail("dfdvf@cc.dedec");
            company.setName("company");
            company.setPhone("dfvdb");
            company.setRuc("fvfgb");
            log.info("company jersey "+company.getName());  
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(WSAudience.class.getName()).log(Level.SEVERE, null, ex);
        }
        return company;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ws;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.columbia.tcc.common.WSRespondeStatus;
import py.edu.columbia.tcc.ejb.jpa.content.DeviceContentFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.ejb.jpa.bean.ws.ContentBean;
import py.edu.columbia.tcc.model.contentHandler.DeviceContent;

/**
 *
 * @author tokio
 */
@Path("/content")
@ManagedBean
public class WSContent implements Serializable {
    private final Logger log = LoggerFactory.getLogger(WSContent.class);

    @Inject
    private DeviceContentFacade deviceContentEJB;

    @GET
    @Path("/get/{device}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContent(@PathParam("device") String device) {
        WSRespondeStatus responseStatus = WSRespondeStatus.REGISTER_ERROR;
        try {
            ContentBean cb = deviceContentEJB.getContentBeanForDevice(device);
            if(cb != null){
                return WSUtil.getResponse(200, cb.getDeviceContentBean());
            } else {
                responseStatus = WSRespondeStatus.NOT_FOUND;
                return WSUtil.getResponse(404, responseStatus.getStatusCode(), responseStatus.getStatusDescription());
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(WSContent.class.getName()).log(Level.SEVERE, null, ex);
            log.error("Error al registrar audiencia de contenido!!: " + ex.getMessage());
            return WSUtil.getResponse(500, responseStatus.getStatusCode(), responseStatus.getStatusDescription());
        }
    }
    
    @GET
    @Path("/download/{content}")
    @Produces("application/zip")
    public Response downloadFile(@PathParam("content") String content) {
        try {
            String path = deviceContentEJB.getContentPath(content);
            log.info("Path "+path);
            
            File file = new File(path);
            if (file.exists()) {
                Response.ResponseBuilder responseBuilder = Response.ok((Object) file);
                responseBuilder.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
                return responseBuilder.build();
            } else {
                return Response.status(404).entity("Contenido no encontrado").build();
            }
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(WSContent.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @GET
    @Path("/prueba/{content}")
    @Produces(MediaType.APPLICATION_JSON)
    public String prueba(@PathParam("content") String content) {
        String path = null;
        try {
            path = deviceContentEJB.getContentPath(content);
            log.info("Path "+path);
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(WSContent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return path;
    }
    
    @GET
    @Path("/comfirmDownload/{content}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response confirmDownload(@PathParam("content") String content) {
        WSRespondeStatus responseStatus = WSRespondeStatus.REGISTER_ERROR;
        Integer update;
        try {
            update = deviceContentEJB.confirmDownload(content);
            if(update > 0){
                responseStatus = WSRespondeStatus.OK;
                return WSUtil.getResponse(200, responseStatus.getStatusCode(), responseStatus.getStatusDescription());
            } 
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(WSContent.class.getName()).log(Level.SEVERE, null, ex);
            return WSUtil.getResponse(500, responseStatus.getStatusCode(), responseStatus.getStatusDescription());
        }
        responseStatus = WSRespondeStatus.NOT_FOUND;
        return WSUtil.getResponse(404, responseStatus.getStatusCode(), responseStatus.getStatusDescription());
    }
}

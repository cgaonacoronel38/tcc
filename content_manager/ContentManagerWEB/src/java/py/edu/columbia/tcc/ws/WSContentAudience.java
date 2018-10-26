/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ws;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.columbia.tcc.common.WSRespondeStatus;
import py.edu.columbia.tcc.ejb.jpa.bean.ws.ContentAudienceBean;
import py.edu.columbia.tcc.ejb.jpa.bean.ws.ResponseStatusBean;
import py.edu.columbia.tcc.ejb.jpa.content.AudienceContentFacade;
import py.edu.columbia.tcc.ejb.jpa.content.ContentFacade;
import py.edu.columbia.tcc.ejb.jpa.content.DeviceFacade;
import py.edu.columbia.tcc.model.chartData.AudienceContent;
import py.edu.columbia.tcc.model.contentHandler.Content;

/**
 *
 * @author tokio
 */
@Path("/contentaudience")
@ManagedBean
public class WSContentAudience implements Serializable {
    private final Logger log = LoggerFactory.getLogger(WSContentAudience.class);
    
    @Inject
    private AudienceContentFacade audienceContentEJB;
    
    @Inject
    private DeviceFacade deviceEJB;
    
    @Inject
    private ContentFacade contentEJB;
    
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(ContentAudienceBean contentAudienceBean) {
        WSRespondeStatus responseStatus = WSRespondeStatus.REGISTER_ERROR;
        try {
            AudienceContent audienceContent = new AudienceContent();
            audienceContent.setDeviceDate(contentAudienceBean.getDeviceDate());
            audienceContent.setServerDate(new Date());
            audienceContent.setRegistrationTime(contentAudienceBean.getRegistrationTime());
            audienceContent.setFromTime(contentAudienceBean.getFromTime());
            audienceContent.setToTime(contentAudienceBean.getToTime());
            audienceContent.setStayTime(contentAudienceBean.getStayTime());
            audienceContent.setIdDevice(deviceEJB.getDeviceIdByUUID(contentAudienceBean.getDevice()));
            audienceContent.setIdContent(contentEJB.getContentIdByUUID(contentAudienceBean.getContent()));
            
            audienceContentEJB.create(audienceContent);
            
            System.out.println("Auidence id: "+audienceContent.getIdAudienceContent());
            
            responseStatus = WSRespondeStatus.OK;
            return WSUtil.getResponse(200, responseStatus.getStatusCode(), responseStatus.getStatusDescription(), Long.valueOf(audienceContent.getIdAudienceContent()));
        } catch (Exception e) {
            log.error("Error al registrar audiencia de contenido!!: " + e.getMessage());
            log.error("Error al registrar audiencia de contenido: ",e);
            return WSUtil.getResponse(500, responseStatus.getStatusCode(), responseStatus.getStatusDescription());
        } 
    }
    
    @GET
    @Path("/prueba")
    @Produces(MediaType.APPLICATION_JSON)
    public ContentAudienceBean prueba() {
        ContentAudienceBean bean = new ContentAudienceBean();
        try {
            bean.setDeviceDate(new Date());
            bean.setRegistrationTime(new Date());
            bean.setStayTime(10);
            bean.setContent(UUID.fromString("1d7e629a-d467-48a0-a1e7-a0b8fa83cbcc"));
            bean.setDevice(UUID.fromString("d8f6b33e-c893-4ac5-97da-a0936e6323d7"));
            bean.setFromTime(new Date());
            bean.setToTime(new Date());
        } catch (Exception ex) {
            log.error("Error al realizar prueba: " + ex.getLocalizedMessage());
        }
        return bean;
    }
}

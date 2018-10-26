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
import py.edu.columbia.tcc.ejb.jpa.bean.ws.DeviceAudienceBean;
import py.edu.columbia.tcc.ejb.jpa.bean.ws.ResponseStatusBean;
import py.edu.columbia.tcc.ejb.jpa.content.AudienceDeviceFacade;
import py.edu.columbia.tcc.ejb.jpa.content.DeviceFacade;
import py.edu.columbia.tcc.model.chartData.AudienceDevice;

/**
 *
 * @author tokio
 */
@Path("/deviceaudience")
@ManagedBean
public class WSDeviceAudience implements Serializable {
    private final Logger log = LoggerFactory.getLogger(WSDeviceAudience.class);
    
    @Inject
    private AudienceDeviceFacade audienceDeviceEJB;
    
    @Inject
    private DeviceFacade deviceEJB;
    
    
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerPing(DeviceAudienceBean deviceAudienceBean) {
        WSRespondeStatus responseStatus = WSRespondeStatus.REGISTER_ERROR;
        try {
            AudienceDevice audienceDevice = new AudienceDevice();
            audienceDevice.setDeviceDate(deviceAudienceBean.getDeviceDate());
            audienceDevice.setServerDate(new Date());
            audienceDevice.setRegistrationTime(deviceAudienceBean.getRegistrationTime());
            audienceDevice.setStayTime(deviceAudienceBean.getStayTime());
            audienceDevice.setIdDevice(deviceEJB.getDeviceIdByUUID(deviceAudienceBean.getDevice()));
            
            audienceDeviceEJB.create(audienceDevice);
            
            System.out.println("Auidence id: "+audienceDevice.getIdAudienceDevice());
            
            responseStatus = WSRespondeStatus.OK;
            return WSUtil.getResponse(200, responseStatus.getStatusCode(), responseStatus.getStatusDescription(), audienceDevice.getIdAudienceDevice());
        } catch (Exception e) {
            log.error("Error al registrar ping: " + e.getLocalizedMessage());
            return WSUtil.getResponse(500, responseStatus.getStatusCode(), responseStatus.getStatusDescription());
        } 
    }
    
    @GET
    @Path("/prueba")
    @Produces(MediaType.APPLICATION_JSON)
    public DeviceAudienceBean prueba() {
        DeviceAudienceBean bean = new DeviceAudienceBean();
        try {
            bean.setDeviceDate(new Date());
            bean.setRegistrationTime(new Date());
            bean.setStayTime(Short.valueOf("10"));
            bean.setDevice(UUID.fromString("d8f6b33e-c893-4ac5-97da-a0936e6323d7"));
        } catch (Exception ex) {
            log.error("Error al realizar prueba: " + ex.getLocalizedMessage());
        }
        return bean;
    }
}

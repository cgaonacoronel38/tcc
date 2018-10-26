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
import py.edu.columbia.tcc.ejb.jpa.bean.ws.DevicePingBean;
import py.edu.columbia.tcc.ejb.jpa.content.DevicePingFacade;

/**
 *
 * @author tokio
 */
@Path("/deviceping")
@ManagedBean
public class WSDevicePing implements Serializable {
    private final Logger log = LoggerFactory.getLogger(WSDevicePing.class);
    
    @Inject
    private DevicePingFacade devicePingEJB;
    
    
    @POST
    @Path("/ping")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerPing(DevicePingBean devicePingBean) {
        WSRespondeStatus responseStatus = WSRespondeStatus.REGISTER_ERROR;
        try {
            py.edu.columbia.tcc.model.chartData.DevicePing devicePing = new py.edu.columbia.tcc.model.chartData.DevicePing();
            devicePing.setDevice(devicePingBean.getDevice());
            devicePing.setCurrentContent(devicePingBean.getCurrentContent());
            devicePing.setCurrentAudienceQuantity(devicePingBean.getCurrentAudienceQuantity());
            devicePing.setDeviceDate(devicePingBean.getDeviceDate());
            devicePing.setServerDate(new Date());
            
            devicePingEJB.edit(devicePing);
            
            responseStatus = WSRespondeStatus.OK;
            return WSUtil.getResponse(200, responseStatus.getStatusCode(), responseStatus.getStatusDescription());
        } catch (Exception e) {
            log.error("Error al registrar ping: " + e.getLocalizedMessage());
            return WSUtil.getResponse(500, responseStatus.getStatusCode(), responseStatus.getStatusDescription());
        } 
    }
    
    @GET
    @Path("/prueba")
    @Produces(MediaType.APPLICATION_JSON)
    public DevicePingBean prueba() {
        DevicePingBean bean = new DevicePingBean();
        try {
            bean.setDeviceDate(new Date());
            bean.setCurrentAudienceQuantity(5);
            //bean.setCurrentContent(UUID.fromString(""));
            bean.setDevice(UUID.fromString("d8f6b33e-c893-4ac5-97da-a0936e6323d7"));
        } catch (Exception ex) {
            log.error("Error al realizar prueba: " + ex.getLocalizedMessage());
        }
        return bean;
    }
}

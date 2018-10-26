/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ws;

import javax.ws.rs.core.Response;
import py.edu.columbia.tcc.ejb.jpa.bean.ws.ResponseStatusBean;

/**
 *
 * @author tokio
 */
public class WSUtil {
    public static Response getResponse(Integer status, Object entity){
        return Response.status(status).entity(entity).build();
    }
    
    public static Response getResponse(Integer status, Integer statusCode, String statusDescription){
        return Response.status(status).entity(new ResponseStatusBean(statusCode, statusDescription)).build();
    }
    
    public static Response getResponse(Integer status, Integer statusCode, String statusDescription, Long authorizationCode){
        return Response.status(status).entity(new ResponseStatusBean(statusCode, statusDescription, authorizationCode)).build();
    }
}

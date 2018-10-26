/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.contentHandler.Device;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.model.chartData.DeviceStatus;

/**
 *
 * @author tid
 */
@Stateless
public class DeviceStatusFacade extends AbstractFacade<DeviceStatus> {
    
    public DeviceStatusFacade(){
        super(DeviceStatus.class);
    }
    
    public List<DeviceStatus> listActiveDevice() throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ds ");
            sb.append("  from DeviceStatus ds ");
            sb.append(" WHERE ds.idDevice.active = ?1");
            
            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, true);
            
            return q.getResultList();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al consultar si la opción de menu existe.", ex);
        } 
    }
    
    public DeviceStatus listByDeviceId(Integer idDevice) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ds ");
            sb.append("  from DeviceStatus ds ");
            sb.append(" WHERE ds.idDevice.idDevice = ?1");
            
            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, idDevice);
            
            return (DeviceStatus)q.getSingleResult();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al consultar si la opción de menu existe.", ex);
        } 
    }
}
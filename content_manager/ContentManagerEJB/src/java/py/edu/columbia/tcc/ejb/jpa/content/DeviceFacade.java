/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.content.Device;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author tid
 */
@Stateless
public class DeviceFacade extends AbstractFacade<Device> {
    
    public DeviceFacade(){
        super(Device.class);
    }
    
    public List<Device> listDevice(Integer idCompany) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT d ");
            sb.append("  FROM Device d ");
            sb.append(" WHERE d.idCompany = ?1");
            
            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, idCompany.longValue());
            
            return q.getResultList();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al consultar si la opción de menu existe.", ex);
        } 
    }
}
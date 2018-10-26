/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.contentHandler.Location;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author tid
 */
@Stateless
public class LocationFacade extends AbstractFacade<Location> {
    
    public LocationFacade(){
        super(Location.class);
    }
    
    public List<Location> listLocation() throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT l ");
            sb.append("  FROM Location l ");
            
            Query q = getEntityManager().createQuery(sb.toString());
            q.setHint("javax.persistence.cache.storeMode", "REFRESH"); 
            
            return q.getResultList();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al consultar localizaciones.", ex);
        } 
    }
}
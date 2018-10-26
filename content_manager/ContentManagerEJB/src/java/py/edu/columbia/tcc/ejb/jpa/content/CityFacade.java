/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.contentHandler.City;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author tid
 */
@Stateless
public class CityFacade extends AbstractFacade<City> {
    
    public CityFacade(){
        super(City.class);
    }
    
    public List<City> listCity(Integer idCountry) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT l ");
            sb.append(" FROM City l ");
            sb.append(" WHERE l.idCountry.idCountry = ?1");
            
            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, idCountry);
            
            return q.getResultList();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al consultar ciudades.", ex);
        } 
    }
}
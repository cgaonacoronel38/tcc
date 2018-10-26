/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import javax.ejb.Stateless;
import javax.persistence.Query;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.chartData.FilteredPlace;

/**
 *
 * @author tid
 */
@Stateless
public class FilteredPlaceFacade extends AbstractFacade<FilteredPlace> {
    
    public FilteredPlaceFacade(){
        super(FilteredPlace.class);
    }
    
    public void insertFilteredPlace(Integer idFilterSetting, Integer idLocation, Integer idCity) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            if(idLocation != null){
                sb.append("INSERT into chart_data.filtered_place (id_filter_setting,id_location) VALUES (?1,?2)");
            } else {
                sb.append("INSERT into chart_data.filtered_place (id_filter_setting,id_city) VALUES (?1,?2)");
            }
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idFilterSetting);
            q.setParameter(2, idLocation != null ? idLocation : idCity);
            
            q.executeUpdate();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al eliminar chart panel.", ex);
        } 
    }
}
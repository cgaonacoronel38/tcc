/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.chartData.FilteredContent;

/**
 *
 * @author tid
 */
@Stateless
public class FilteredContentFacade extends AbstractFacade<FilteredContent> {
    
    public FilteredContentFacade(){
        super(FilteredContent.class);
    }
    
    public void deleteFilteredContent(Integer idFilterSetting) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM FilteredContent f ");
            sb.append(" WHERE f.filteredContentPK.idFilterSetting = ?1");
            
            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, idFilterSetting);
            
            q.executeUpdate();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al eliminar chart panel.", ex);
        } 
    }
}
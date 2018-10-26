/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.chartData.FilterSetting;

/**
 *
 * @author tid
 */
@Stateless
public class FilterSettingFacade extends AbstractFacade<FilterSetting> {
    
    public FilterSettingFacade(){
        super(FilterSetting.class);
    }
    
    public void deleteFilterSetting(Integer idChartPanel) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM chart_data.filter_setting WHERE id_filter_setting in ( ");
            sb.append(" SELECT id_filter_setting from chart_data.chart_panel WHERE  id_chart_panel = ?1)");
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idChartPanel);
            
            q.executeUpdate();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al eliminar chart panel.", ex);
        } 
    }
}
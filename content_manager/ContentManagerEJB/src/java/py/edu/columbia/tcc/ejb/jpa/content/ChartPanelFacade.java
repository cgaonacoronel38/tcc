/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.ejb.jpa.bean.AudienceDataChart;
import py.edu.columbia.tcc.ejb.jpa.bean.AudienceDataChartInfo;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.chartData.ChartPanel;

/**
 *
 * @author tid
 */
@Stateless
public class ChartPanelFacade extends AbstractFacade<ChartPanel> {

    public ChartPanelFacade() {
        super(ChartPanel.class);
    }

    public void deleteChartPanel(Integer idChartPanel) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ChartPanel c  ");
            sb.append(" WHERE c.idChartPanel = ?1 ");

            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, idChartPanel);

            q.executeUpdate();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al eliminar chart panel.", ex);
        }
    }

    public List<AudienceDataChart> getAudienceDataChart(Integer idChartPanel) throws GDMEJBException {
        List<AudienceDataChart> result = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select * from chart_data.fn_get_audience(?1)");

            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idChartPanel);

            List<Object[]> resulset = q.getResultList();
            if (resulset != null) {
                result = new ArrayList<>();
                for (Object[] row : resulset) {
                    AudienceDataChart audience = new AudienceDataChart();
                    audience.setReference((String) row[0]);
                    audience.setIdentifier((Integer) row[1]);
                    audience.setDescription((String) row[2]);
                    audience.setQuantity((Integer) row[3]);
                    result.add(audience);
                }
            }
        } catch (Exception ex) {
            System.err.println("Id_chartPanel: " + idChartPanel);
            System.err.println("Error al obtener datos de audiencia:  " + ex.getMessage());
            System.err.println(ex);
            throw new GDMEJBException("Error al obtener datos de chart", ex);
        }
        return result;
    }

    public AudienceDataChartInfo getAudienceDataInfo(Integer idChartPanel) throws GDMEJBException {
        AudienceDataChartInfo audience = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select ");
            sb.append("	cp.title title,");
            sb.append("	tf.description type_audience,");
            sb.append("	tc.description type_chart,");
            sb.append("	tt.description type_time,");
            sb.append("	tp.description type_place,");
            sb.append("	case ");
            sb.append("		when fs.all_contents and tf.id_filter = 1 then 'Todos'");
            sb.append("		when fs.all_contents = false and tf.id_filter = 1 then 'Filtrados'");
            sb.append("	end contents,");
            sb.append("	case ");
            sb.append("		when fs.all_places then 'Todos'");
            sb.append("		else 'Filtrados'");
            sb.append("	end places ");
            sb.append("from chart_data.chart_panel cp ");
            sb.append("join chart_data.filter_setting fs on cp.id_filter_setting = fs.id_filter_setting ");
            sb.append("join chart_data.type_chart tc on tc.id_type_chart = cp.id_type_chart ");
            sb.append("join chart_data.type_time tt on tt.id_type_time = fs.id_type_time ");
            sb.append("join chart_data.type_filter tf on tf.id_filter = fs.id_type_filter ");
            sb.append("join chart_data.type_place tp on tp.id_type_place = fs.id_type_place ");
            sb.append("where cp.id_chart_panel =  ?1");

            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idChartPanel);

            List<Object[]> resulset = q.getResultList();
            if (resulset != null) {
                for (Object[] row : resulset) {
                    audience = new AudienceDataChartInfo();
                    audience.setTitle((String) row[0]);
                    audience.setTypeAudience((String) row[1]);
                    audience.setTypeChart((String) row[2]);
                    audience.setTypeTime((String) row[3]);
                    audience.setTypePlace((String) row[4]);
                    audience.setContens((String) row[5]);
                    audience.setPlaces((String) row[6]);
                }
            }
        } catch (Exception ex) {
            throw new GDMEJBException("Error al obtener datos de chart panel. sb.append(", ex);
        }
        return audience;
    }
}

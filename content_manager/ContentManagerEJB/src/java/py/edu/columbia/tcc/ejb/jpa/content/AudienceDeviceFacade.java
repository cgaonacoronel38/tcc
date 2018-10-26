/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.ejb.jpa.bean.ChartDeviceBean;
import py.edu.columbia.tcc.model.bean.AudienceBean;
import py.edu.columbia.tcc.model.chartData.AudienceDevice;

/**
 *
 * @author tid
 */
@Stateless
public class AudienceDeviceFacade extends AbstractFacade<AudienceDevice> {

    public AudienceDeviceFacade() {
        super(AudienceDevice.class);
    }

    public List<AudienceBean> lastDayAudience(Integer idDevice) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select group_time, quantity from chart_data.lastDayAudienceDevice ");
        sb.append(" where id_device = ?1 ");

        return getQueryData(sb.toString(), idDevice);
    }

    public List<AudienceBean> lastDayAudienceGroup() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select device, quantity from chart_data.lastDayAudienceDeviceGroup ");

        return getQueryData(sb.toString(), 0);
    }

    public List<AudienceBean> lastWeekAudience(Integer idDevice) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select group_time, quantity from chart_data.lastWeekAudienceDevice ");
        sb.append(" where id_device = ?1 ");

        return getQueryData(sb.toString(), idDevice);
    }

    public List<AudienceBean> lastWeekaudienceGroup() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select device, quantity from chart_data.lastWeekAudienceDeviceGroup ");

        return getQueryData(sb.toString(), 0);
    }

    public List<ChartDeviceBean> getChartDevices(Integer idFilterSetting) {
        List<ChartDeviceBean> listChartDevices = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select * from chart_data.get_chart_devices(?1) ");

            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idFilterSetting);

            List<Object[]> resulset = q.getResultList();
            listChartDevices = new ArrayList<>();
            for (Object[] row : resulset) {
                ChartDeviceBean bean = new ChartDeviceBean((Integer) row[0], (String) row[1]);
                listChartDevices.add(bean);
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AudienceDeviceFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listChartDevices;
    }

    private List<AudienceBean> getQueryData(String query, Integer idDevice) {
        List<AudienceBean> listAudiences = null;
        try {
            Query q = getEntityManager().createNativeQuery(query);
            if (idDevice > 0) {
                q.setParameter(1, idDevice);
            }

            List<Object[]> resulset = q.getResultList();

            if (resulset != null && !resulset.isEmpty()) {
                listAudiences = new ArrayList<>();
                for (Object[] row : resulset) {
                    AudienceBean bean = new AudienceBean((String) row[0], castToLong(row[1]));
                    listAudiences.add(bean);
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(AudienceDeviceFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAudiences;
    }

    private long castToLong(Object value) {
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).longValue();
        }

        if (value instanceof BigInteger) {
            return ((BigInteger) value).longValue();
        }

        return 0;
    }
}

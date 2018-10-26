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
import py.edu.columbia.tcc.model.contentHandler.Audiences;

/**
 *
 * @author tid
 */
@Stateless
public class AudienceFacade extends AbstractFacade<Audiences> {
    
    public AudienceFacade(){
        super(Audiences.class);
    }
    
    public List<AudienceBean> audienceQuantityDeviceToday(Integer idDevice){
        List<AudienceBean> listAudiences = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select reference, quantity from chart_data.view_deviceaudienceday ");
            sb.append(" where id_device = ?1 ");
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idDevice);
            
            List<Object[]> resulset = q.getResultList();
            listAudiences = new ArrayList<>();
            for(Object[] row : resulset){
                AudienceBean bean = new AudienceBean((String)row[0], ((BigInteger)row[1]).longValue());
                listAudiences.add(bean);
            }
        } catch (Exception ex) {
           java.util.logging.Logger.getLogger(AudienceFacade.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return listAudiences;
    }
    
    public List<AudienceBean> audienceQuantityDeviceTodayGroup(){
        List<AudienceBean> listAudiences = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select (select description from content_handler.device where id_device = a.id_device) reference,  ");
            sb.append(" sum(a.quantity) quantity");
            sb.append(" from chart_data.view_deviceaudienceday a ");
            sb.append(" group by a.id_device ");
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            
            List<Object[]> resulset = q.getResultList();
            listAudiences = new ArrayList<>();
            for(Object[] row : resulset){
                AudienceBean bean = new AudienceBean((String)row[0], ((BigDecimal)row[1]).longValue());
                listAudiences.add(bean);
            }
        } catch (Exception ex) {
           java.util.logging.Logger.getLogger(AudienceFacade.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return listAudiences;
    }
    
    public List<AudienceBean> audienceQuantityDeviceWeek(Integer idDevice){
       List<AudienceBean> listAudiences = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select reference, quantity from chart_data.view_deviceaudienceweek ");
            sb.append(" where id_device = ?1 ");
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idDevice);
            
            List<Object[]> resulset = q.getResultList();
            listAudiences = new ArrayList<>();
            for(Object[] row : resulset){
                AudienceBean bean = new AudienceBean((String)row[0], ((BigInteger)row[1]).longValue());
                listAudiences.add(bean);
            }
        } catch (Exception ex) {
           java.util.logging.Logger.getLogger(AudienceFacade.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return listAudiences;
    }
    
    public List<AudienceBean> audienceQuantityDeviceWeekGroup(){
        List<AudienceBean> listAudiences = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select (select description from content_handler.device where id_device = a.id_device) reference,  ");
            sb.append(" sum(a.quantity) quantity");
            sb.append(" from chart_data.view_deviceaudienceweek a ");
            sb.append(" group by a.id_device ");
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            
            List<Object[]> resulset = q.getResultList();
            listAudiences = new ArrayList<>();
            for(Object[] row : resulset){
                AudienceBean bean = new AudienceBean((String)row[0], ((BigDecimal)row[1]).longValue());
                listAudiences.add(bean);
            }
        } catch (Exception ex) {
           java.util.logging.Logger.getLogger(AudienceFacade.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return listAudiences;
    }
    
    public List<ChartDeviceBean> getChartDevices(Integer idFilterSetting){
        List<ChartDeviceBean> listChartDevices = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select * from chart_data.get_chart_devices(?1) ");
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idFilterSetting);
            
            List<Object[]> resulset = q.getResultList();
            listChartDevices = new ArrayList<>();
            for(Object[] row : resulset){
                ChartDeviceBean bean = new ChartDeviceBean((Integer)row[0], (String)row[1]);
                listChartDevices.add(bean);
            }
        } catch (Exception ex) {
           java.util.logging.Logger.getLogger(AudienceFacade.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return listChartDevices;
    }
}
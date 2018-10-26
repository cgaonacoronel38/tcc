/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.ejb.jpa.bean.DeviceStatusBean;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.chartData.DevicePing;

/**
 *
 * @author tid
 */
@Stateless
public class DevicePingFacade extends AbstractFacade<DevicePing> {
    
    public DevicePingFacade(){
        super(DevicePing.class);
    }
    
    public List<DeviceStatusBean> listDeviceStatus() throws GDMEJBException {
        try {
            List<DeviceStatusBean> listDeviceStatusBean = null;
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * from chart_data.view_device_status ");
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            
            List<Object[]> result = q.getResultList();
            if(result != null && !result.isEmpty()){
                listDeviceStatusBean = new ArrayList<>();
                for(Object[] row : result){
                    DeviceStatusBean deviceStatusBean = new DeviceStatusBean();
                    deviceStatusBean.setDevice((String) row[0]);
                    deviceStatusBean.setCurrentContent((String) row[1]);
                    deviceStatusBean.setCurrentAudienceQuantity((Integer) row[2]);
                    deviceStatusBean.setDeviceDate((Date) row[3]);
                    deviceStatusBean.setServerDate((Date) row[4]);
                    deviceStatusBean.setLocationName((String) row[5]);
                    deviceStatusBean.setLocationDescription((String) row[6]);
                    deviceStatusBean.setCountry((String) row[7]);
                    deviceStatusBean.setCity((String) row[8]);
                    deviceStatusBean.setIdDevice((Integer) row[9]);
                    deviceStatusBean.setGeoreference((String) row[10]);
                    
                    listDeviceStatusBean.add(deviceStatusBean);
                }
            }
            
            return listDeviceStatusBean;
        } catch (Exception ex) {
            throw new GDMEJBException("Error al consultar estado de dispositivos.", ex);
        } 
    }
    
    public DeviceStatusBean listDeviceStatusByIdDevice(Integer idDevice) throws GDMEJBException {
        try {
            DeviceStatusBean deviceStatusBean = null;
            StringBuilder sb = new StringBuilder();
            sb.append("select * from chart_data.view_device_status where id_device = ?1 ");
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idDevice);
            
            Object[] row = (Object[])q.getSingleResult();
            if(row != null){
                    deviceStatusBean = new DeviceStatusBean();
                    deviceStatusBean.setDevice((String) row[0]);
                    deviceStatusBean.setCurrentContent((String) row[1]);
                    deviceStatusBean.setCurrentAudienceQuantity((Integer) row[2]);
                    deviceStatusBean.setDeviceDate((Date) row[3]);
                    deviceStatusBean.setServerDate((Date) row[4]);
                    deviceStatusBean.setLocationName((String) row[5]);
                    deviceStatusBean.setLocationDescription((String) row[6]);
                    deviceStatusBean.setCountry((String) row[7]);
                    deviceStatusBean.setCity((String) row[8]);
                    deviceStatusBean.setIdDevice((Integer) row[9]);
                    deviceStatusBean.setGeoreference((String) row[10]);
            }
            
            return deviceStatusBean;
        } catch (Exception ex) {
            throw new GDMEJBException("Error al obtener estado de dispositivo.", ex);
        } 
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import java.util.ArrayList;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.contentHandler.Device;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.ejb.jpa.bean.ContentDeviceBean;
import static py.edu.columbia.tcc.ejb.jpa.content.AbstractFacade.log;

/**
 *
 * @author tid
 */
@Stateless
public class DeviceFacade extends AbstractFacade<Device> {

    public DeviceFacade() {
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

    public List<ContentDeviceBean> listDevices() {
        List<ContentDeviceBean> listDevices = null;
        try {
            String sql = "select d.id_device, d.\"name\" deviceName, d.description deviceDesciption, "
                    + "l.\"name\" locationName, l.description locationDescription, c.description city, "
                    + "l.latitude || ',' || l.longitude geolocation,  d.active active "
                    + "from content_handler.device d "
                    + "join content_handler.location l on d.id_location = l.id_location "
                    + "join content_handler.city c on l.id_city = c.id_city ";

            Query q = getEntityManager().createNativeQuery(sql);

            List<Object[]> resulset = q.getResultList();
            if (resulset != null) {
                listDevices = new ArrayList<>();
                for (Object[] row : resulset) {
                    ContentDeviceBean cb = new ContentDeviceBean();
                    cb.setIdDevice((Integer) row[0]);
                    cb.setDeviceName((String) row[1]);
                    cb.setDeviceDescription((String) row[2]);
                    cb.setLocationName((String) row[3]);
                    cb.setLocationDescription((String) row[4]);
                    cb.setCity((String) row[5]);
                    cb.setGeolocation((String) row[6]);
                    cb.setActive((boolean) row[7]);
                    listDevices.add(cb);
                }
            } else {
                System.err.println("Resulset nulo");
            }
        } catch (Exception ex) {
            log.error("Error al obtener device content: " + ex.getMessage());
            java.util.logging.Logger.getLogger(DeviceContentFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDevices;
    }

    public List<Device> listActiveDevice() throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT d ");
            sb.append("  FROM Device d ");
            sb.append(" WHERE d.active = ?1");

            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, true);

            return q.getResultList();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al consultar si la opción de menu existe.", ex);
        }
    }

    public Device getDeviceIdByUUID(UUID uuid) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT d ");
            sb.append("  FROM Device d ");
            sb.append(" WHERE d.uuid = ?1");

            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, uuid);

            return (Device) q.getSingleResult();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al consultar si la opción de menu existe.", ex);
        }
    }
    
    public Integer updateStatus(Integer idDevice, boolean status) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update content_handler.device set active = ?1 where id_device = ?2 ");

            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, status);
            q.setParameter(2, idDevice);

            return q.executeUpdate();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al consultar si la opción de menu existe.", ex);
        }
    }
}

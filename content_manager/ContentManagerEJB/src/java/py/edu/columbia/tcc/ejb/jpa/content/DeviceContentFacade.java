/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import java.util.ArrayList;
import java.util.Date;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.contentHandler.DeviceContent;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.ejb.jpa.bean.ContentDeviceBean;
import py.edu.columbia.tcc.ejb.jpa.bean.ws.ContentBean;
import py.edu.columbia.tcc.ejb.jpa.bean.ws.DeviceContentBean;

@Stateless
public class DeviceContentFacade extends AbstractFacade<DeviceContent> {

    public DeviceContentFacade() {
        super(DeviceContent.class);
    }

    public List<DeviceContent> listDeviceContent(Integer idContent) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c ");
            sb.append("  FROM DeviceContent c ");
            sb.append("  WHERE c.idContent = ?1 ");

            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, idContent);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return q.getResultList();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DeviceContentFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new GDMEJBException("Error al listar dispositivos de contenidos.", ex);
        }
    }

    public String getContentPathNative(Integer idDevice) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("select c.path ");
            sb.append("from content_handler.device_content dc, content_handler c ");
            sb.append("where  dc.id_device = ?1 ");
            sb.append("and dc.active is true ");
            sb.append("and dc.processed is false ");
            sb.append("and dc.id_content = c.id_content ");
            sb.append("order by dc.id_content limit 1 ");

            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, idDevice);

            return (String) q.getSingleResult();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al obtener contenido para dispositivo.", ex);
        }
    }

    public ContentBean getContentBeanForDevice(String device) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("select * from content_handler.view_device_content ");
            sb.append(" where uuid_device like ?1");

            System.out.println("Query content device: " + sb.toString());

            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, device);

            Object[] resulset = (Object[]) q.getSingleResult();
            ContentBean cb = null;
            if (resulset != null) {
                System.err.println("parseando datos");
                cb = new ContentBean();
                DeviceContentBean dcb = new DeviceContentBean();
                dcb.setName((String) resulset[0]);
                dcb.setDescription((String) resulset[1]);
                cb.setDirectory((String) resulset[2]);
                dcb.setDuration((Integer) resulset[3]);
                cb.setActive((boolean) resulset[4]);
                cb.setDownloaded((boolean) resulset[5]);
                dcb.setDueDate((Date) resulset[6]);
                dcb.setUuid(UUID.fromString((String) resulset[8]));
                cb.setDeviceContentBean(dcb);
            } else {
                System.err.println("Resulset nulo");
            }
            return cb;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DeviceContentFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new GDMEJBException("Error al obtener contenido .", ex);
        }
    }

    public String getContentPath(String content) throws GDMEJBException {
        try {
            UUID uuid = UUID.fromString(content);

            StringBuilder sb = new StringBuilder();
            sb.append("SELECT dc.directory ");
            sb.append("  FROM DeviceContent dc ");
            sb.append("  WHERE dc.uuid = ?1 ");

            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, uuid);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return (String) q.getSingleResult();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al obtener ruta de contanido." + ex.getMessage());
        }
    }

    public Integer confirmDownload(String content) throws GDMEJBException {
        try {
            UUID uuid = UUID.fromString(content);

            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE DeviceContent dc ");
            sb.append(" SET dc.active = FALSE, dc.downloaded = true, dc.dateDownload = ?1");
            sb.append(" where dc.uuidContent = ?2 ");

            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, new Date());
            q.setParameter(2, uuid);

            return q.executeUpdate();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al obtener ruta de contanido." + ex.getMessage());
        }
    }

    public List<ContentDeviceBean> getContentDevice(Integer idContent) {
        List<ContentDeviceBean> listContentDevice = null;
        try {
            String sql = "select d.id_device device,  "
                    + "	   d.\"name\" device_name,   "
                    + "	   d.description device_description,  "
                    + "	   l.\"name\" location_name,  "
                    + "	   l.description location_description,  "
                    + "	   c.description city,  "
                    + "	   dc.active active  "
                    + "from content_handler.device_content dc  "
                    + "join content_handler.device d on dc.id_device = d.id_device  "
                    + "join content_handler.location l on l.id_location = d.id_location  "
                    + "join content_handler.city c on c.id_city = l.id_city  "
                    + "where dc.id_content = ?1 "
                    + "and dc.active is true ";

            Query q = getEntityManager().createNativeQuery(sql);
            q.setParameter(1, idContent);

            List<Object[]> resulset = (List<Object[]>) q.getResultList();

            if (resulset != null) {
                listContentDevice = new ArrayList<>();
                for (Object[] row : resulset) {
                    ContentDeviceBean cb = new ContentDeviceBean();
                    cb.setIdDevice((Integer) row[0]);
                    cb.setDeviceName((String) row[1]);
                    cb.setDeviceDescription((String) row[2]);
                    cb.setLocationName((String) row[3]);
                    cb.setLocationDescription((String) row[4]);
                    cb.setCity((String) row[5]);
                    cb.setActive((boolean) row[6]);
                    listContentDevice.add(cb);
                }
            } else {
                System.err.println("Resulset nulo");
            }
        } catch (Exception ex) {
            log.error("Error al obtener device content: " + ex.getMessage());
            java.util.logging.Logger.getLogger(DeviceContentFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listContentDevice;
    }

    public List<ContentDeviceBean> getContentDevicefiltered(Integer idContent) {
        List<ContentDeviceBean> listContentDevice = null;
        try {
            String sql = "select d.id_device device, "
                    + "	   d.\"name\" device_name,  "
                    + "	   d.description device_description, "
                    + "	   l.\"name\" location_name, "
                    + "	   l.description location_description, "
                    + "	   c.description city "
                    + "from content_handler.device d "
                    + "join content_handler.location l on l.id_location = d.id_location "
                    + "join content_handler.city c on c.id_city = l.id_city "
                    + "where d.active is true "
                    + "and d.id_device not in ( "
                    + "  select id_device from content_handler.device_content "
                    + "  where id_content = ?1 "
                    + ")";
            Query q = getEntityManager().createNativeQuery(sql);
            q.setParameter(1, idContent);

            List<Object[]> resulset = (List<Object[]>) q.getResultList();

            if (resulset != null) {
                listContentDevice = new ArrayList<>();
                for (Object[] row : resulset) {
                    ContentDeviceBean cb = new ContentDeviceBean();
                    cb.setIdDevice((Integer) row[0]);
                    cb.setDeviceName((String) row[1]);
                    cb.setDeviceDescription((String) row[2]);
                    cb.setLocationName((String) row[3]);
                    cb.setLocationDescription((String) row[4]);
                    cb.setCity((String) row[5]);
                    cb.setActive(true);
                    listContentDevice.add(cb);
                }
            } else {
                System.err.println("Resulset nulo");
            }
        } catch (Exception ex) {
            log.error("Error al obtener device content: " + ex.getMessage());
            java.util.logging.Logger.getLogger(DeviceContentFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listContentDevice;
    }

    public void createContentDevice(Integer idcontent, Integer idDevice, Date dateDue) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select content_handler.insert_content_device(?1,?2,?3) ");

            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idcontent);
            q.setParameter(2, idDevice);
            q.setParameter(3, dateDue);

            q.getSingleResult();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al insetar device content" + ex.getMessage());
        }
    }

    public void inactiveAllContentDevices(Integer idContent) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update content_handler.device_content set active = false, downloaded = false, date_inactive = now() where id_content = ?1 ");

            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idContent);

            q.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Error al obtener uuid de contenido: " + ex.getMessage());
            System.err.println(ex);
            throw new GDMEJBException("Error al obtener identificador de contenido", ex);
        }
    }

    public void inactiveContentDevices(Integer idContent, Integer idDevice) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update content_handler.device_content set active = false, downloaded = false, date_inactive = now() where id_content = ?1 and id_device = ?2 ");

            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idContent);
            q.setParameter(2, idDevice);

            q.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Error al obtener uuid de contenido: " + ex.getMessage());
            System.err.println(ex);
            throw new GDMEJBException("Error al obtener identificador de contenido", ex);
        }
    }
}

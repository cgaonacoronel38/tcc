/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.content.Content;
import py.edu.columbia.tcc.model.content.DeviceContent;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.model.bean.ContentBean;

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
            sb.append("  WHERE c.content.idContent = ?1 ");

            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, idContent);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return q.getResultList();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al listar dispositivos de contenidos.", ex);
        }
    }

    public String getContentForDeviceNative(Integer idDevice) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("select c.path ");
            sb.append("from content_handler.device_content dc, content_handler.content c ");
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

            UUID uuid = UUID.fromString(device);

            sb.append("SELECT dc FROM DeviceContent dc ");
            sb.append("  WHERE dc.active = TRUE and dc.downloaded = FALSE and dc.device.uuid = ?1 ");
            sb.append("  ORDER BY dc.content.idContent ");

            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, uuid);
            q.setMaxResults(1);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            DeviceContent dc = (DeviceContent) q.getSingleResult();
            ContentBean cb = new ContentBean();
            if (dc != null) {
                Content c = dc.getContent();
                cb.setUuid(dc.getUuid());
                cb.setIdCompany(c.getIdCompany());
                cb.setIdContent(c.getIdContent());
                cb.setName(c.getName());
                cb.setDescription(c.getDescription());
            }
            return cb;
        } catch (Exception ex) {
            throw new GDMEJBException("Error al obtener contenido .", ex);
        }
    }
    
    public String getContentPath(String content) throws GDMEJBException {
        try {
            UUID uuid = UUID.fromString(content);

            StringBuilder sb = new StringBuilder();
            sb.append("SELECT dc.content.directory ");
            sb.append("  FROM DeviceContent dc ");
            sb.append("  WHERE dc.uuid = ?1 ");

            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, uuid);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");

            return (String) q.getSingleResult();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al obtener ruta de contanido."+ ex.getMessage());
        }
    }
    
    public Integer confirmDownload(String content) throws GDMEJBException {
        try {
            UUID uuid = UUID.fromString(content);

            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE DeviceContent dc ");
            sb.append(" SET dc.active = FALSE, dc.downloaded = true ");
            sb.append(" where dc.uuid = ?1 ");

            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, uuid);

            return q.executeUpdate();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al obtener ruta de contanido."+ ex.getMessage());
        }
    }
    
}

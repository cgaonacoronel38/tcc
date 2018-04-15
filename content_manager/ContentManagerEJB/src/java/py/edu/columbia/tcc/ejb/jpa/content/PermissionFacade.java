/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import py.edu.columbia.tcc.exception.GDMEJBException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import py.edu.columbia.tcc.login.model.MenuPermission;

/**
 *
 * @author tid
 */
@Stateless
public class PermissionFacade extends AbstractFacade<MenuPermission> {
    
    public PermissionFacade(){
        super(MenuPermission.class);
    }
    
    public boolean isPermissionNATIVE(int idcompany, int idsysuser, String url) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select mp.access ");
            sb.append(" from system_handler.menu_permission mp join system_handler.menu m on m.id_menu = mp.id_menu ");
            sb.append(" join system_handler.role r ON r.id_role = mp.id_role ");
            sb.append(" join system_handler.user u ON u.id_role = r.id_role ");
            sb.append(" where m.url = ?1 and u.id_user = ?2 and u.id_company = ?3 ");
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, url.trim().toLowerCase());
            q.setParameter(2, idsysuser);
            q.setParameter(3, idcompany);
            
            List l = q.getResultList();
            
            if(l == null || l.isEmpty()) {
                return false;
            }
            
            return (Boolean) l.get(0);
        } catch (Exception ex) {
            throw new GDMEJBException("No se pudo consultar el permiso, idcompany: " + idcompany + ", idsysuser: " + idsysuser + ", url: " + url, ex);
        }
    }
    
    public Boolean isUrlValidNATIVE(String url) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT active ");
            sb.append("  FROM system_handler.menu ");
            sb.append(" WHERE url = ?1");
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, url.trim().toLowerCase());
            
            Object o = null;
            
            try {
                o = q.getSingleResult();
                if(o == null) return false; //tiene null en el campo active
            } catch (NoResultException ex) {
                return null;
            }
            
            return Boolean.valueOf(o.toString());
        } catch (Exception ex) {
            throw new GDMEJBException("Error al consultar si la opción de menu existe.", ex);
        } 
    }
}
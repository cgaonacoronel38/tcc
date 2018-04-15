/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.ejb.jpa;

import py.edu.columbia.tcc.login.model.MenuPermission;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author eduardo
 */
@Stateless
public class PemissionFacade extends AbstractFacade<MenuPermission> {

    public PemissionFacade() {
        super(MenuPermission.class);
    }

    public boolean isPermissionNATIVE(int idcompany, int idsysuser, String url) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT p.active ");
            sb.append(" FROM system_handler.menu_permission p ");
            sb.append(" JOIN system_handler.menu m ON p.id_menu= m.id_menu ");
            sb.append(" WHERE p.id_company = ?1 ");
            sb.append(" AND p.id_user = ?2  ");
            sb.append(" AND m.url = ?3 ");

//            sb.append("SELECT position(m.url in ?3) ");
//            sb.append(" FROM sys_menu_permission p ");
//            sb.append(" JOIN sys_menu m ON p.idmenu = m.idmenu ");
//            sb.append(" WHERE p.idcompany = ?1 ");
//            sb.append(" AND p.idsysuser = ?2 ");

            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idcompany);
            q.setParameter(2, idsysuser);
            q.setParameter(3, url.trim().toLowerCase());

            List l = q.getResultList();
            
            if(l == null || l.isEmpty()) {
                return false;
            }
            
            return (Boolean) l.get(0);
        } catch (Exception ex) {
            throw new Exception("No se pudo consultar el permiso, idcompany: " + idcompany + ", idsysuser: " + idsysuser + ", url: " + url, ex);
        }
    }

    public Boolean isUrlValidNATIVE(String url) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT active ");
            sb.append(" FROM system_handler.menu ");
            sb.append(" WHERE url = ?1");

            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, url.trim().toLowerCase());

            List l = q.getResultList();
            
            if(l == null || l.isEmpty()) {
                return false;
            }
            
            return (Boolean) l.get(0);

        } catch (Exception ex) {
            throw new Exception("Error al consultar si la opción de menu existe.", ex);
        }

    }

}

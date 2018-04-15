/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.ejb.jpa;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.login.model.SysParam;

/**
 *
 * @author tid
 */
@Stateless
public class SysParamFacade extends AbstractFacade<SysParam> {

    public SysParamFacade() {
        super(SysParam.class);
    }

    public List<SysParam> listSysParam() {

        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT sp ");
        jpql.append("  FROM SysParam sp ");

        Query q = getEntityManager().createQuery(jpql.toString());

        return q.getResultList();
    }

    public String findById(int idCompany, String key) throws Exception {
        try {
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT sp.value ");
            jpql.append("  FROM system_handler.sys_param sp ");
            jpql.append(" WHERE sp.id_company = ?1 ");
            jpql.append("   AND sp.key = ?2 ");

            Query q = getEntityManager().createNativeQuery(jpql.toString());
            q.setParameter(1, idCompany);
            q.setParameter(2, key);

            List l = q.getResultList();

            if (l == null || l.isEmpty()) {
                throw new Exception(String.format("El parámetro de sistema [%s - %s] no existe.",
                        idCompany, key));
            }

            return l.get(0).toString();
        } catch (Exception ex) {
            throw new Exception("Error al obtener un parámetro de sistema.", ex);
        }
    }
}

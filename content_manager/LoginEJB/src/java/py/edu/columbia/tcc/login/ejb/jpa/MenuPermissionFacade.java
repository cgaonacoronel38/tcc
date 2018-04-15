package py.edu.columbia.tcc.login.ejb.jpa;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import py.edu.columbia.tcc.login.model.MenuPermission;

@Stateless
public class MenuPermissionFacade extends AbstractFacade<MenuPermission>{

    public MenuPermissionFacade() {
        super(MenuPermission.class);
    }
    
    public List<MenuPermission> list() throws Exception{
        try{
            StringBuilder sb = new StringBuilder();
            sb.append("select e ");
            sb.append(" from MenuPermission e ");
            
            Query q = getEntityManager().createQuery(sb.toString());
            return q.getResultList();
        } catch(Exception e){
            throw new Exception("Error al listar permisos de menú "+ e.getLocalizedMessage());
        }  
    }
}

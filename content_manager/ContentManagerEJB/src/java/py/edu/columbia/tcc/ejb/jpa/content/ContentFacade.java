/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.content.Content;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class ContentFacade extends AbstractFacade<Content> {
    
    public ContentFacade(){
        super(Content.class);
    }
    
    public List<Content> listContent() throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c ");
            sb.append("  FROM Content c ");
            
            Query q = getEntityManager().createQuery(sb.toString());
            q.setHint("javax.persistence.cache.storeMode", "REFRESH"); 
            
            return q.getResultList();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al listar contenidos.", ex);
        } 
    }
}
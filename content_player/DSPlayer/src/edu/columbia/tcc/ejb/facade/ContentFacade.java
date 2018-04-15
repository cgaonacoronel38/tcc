/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.columbia.tcc.ejb.facade;

import edu.columbia.tcc.ejb.AbstractFacade;
import edu.columbia.tcc.ejb.entity.Content;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author tokio
 */
public class ContentFacade extends AbstractFacade<Content> {
    
    public ContentFacade(){
        super(Content.class);
    }
    
    public List<String> lisContent() throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT c.uuid ");
            sb.append(" from Content c ");
            sb.append(" where c.active = true ");
            
            Query q = getEntityManager().createQuery(sb.toString());
            q.setHint("javax.persistence.cache.storeMode", "REFRESH"); 
            
            return q.getResultList();
        } catch (Exception ex) {
            throw new Exception("Error al listar contenidos.", ex);
        } 
    }
}

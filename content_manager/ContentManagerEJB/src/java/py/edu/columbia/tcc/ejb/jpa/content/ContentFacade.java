/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.contentHandler.Content;
import java.util.List;
import java.util.UUID;
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
            sb.append("  WHERE c.active = true ");
            
            Query q = getEntityManager().createQuery(sb.toString());
            q.setHint("javax.persistence.cache.storeMode", "REFRESH"); 
            
            return q.getResultList();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al listar contenidos.", ex);
        } 
    }
    
    public Content getContentIdByUUID(UUID uuid) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT d ");
            sb.append("  FROM Content d ");
            sb.append(" WHERE d.uuid = ?1");
            
            Query q = getEntityManager().createQuery(sb.toString());
            q.setParameter(1, uuid);
            
            return (Content)q.getSingleResult();
        } catch (Exception ex) {
            System.err.println("Error al obtener uuid de contenido: "+ex.getMessage());
            System.err.println(ex);
            throw new GDMEJBException("Error al obtener identificador de contenido", ex);
        } 
    }
    
    public void inactiveContent(Integer idContent) throws GDMEJBException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("update content_handler.content set active = false where id_content = ?1 ");
            
            Query q = getEntityManager().createNativeQuery(sb.toString());
            q.setParameter(1, idContent);
            
            q.executeUpdate();
        } catch (Exception ex) {
            System.err.println("Error al obtener uuid de contenido: "+ex.getMessage());
            System.err.println(ex);
            throw new GDMEJBException("Error al obtener identificador de contenido", ex);
        } 
    }
}
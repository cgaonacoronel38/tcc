package py.edu.columbia.tcc.login.ejb.jpa;


import py.edu.columbia.tcc.login.common.AESSymetricCrypto;
import py.edu.columbia.tcc.login.manager.PersistenceManager;
import py.edu.columbia.tcc.login.manager.ResourceManager;
import py.edu.columbia.tcc.login.setup.AppSetup;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tokio
 */
public abstract class AbstractFacade<T> {
    protected final static Logger log = LoggerFactory.getLogger(AbstractFacade.class);
    
    @PersistenceContext(unitName = "LoginEJBPU")
    private EntityManager emDcors;
    
    private Class<T> entityClass;
    
    private Validator validator;   
    
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected Validator getValidator() throws Exception, Exception {
        if(validator == null) {
            AppSetup as = ResourceManager.getInstance().getAppSetup();
            
            if(as.isMaster()) {
                this.validator = PersistenceManager.getInstance(as.getPuMaster())
                    .getValidatorFactory().getValidator();            
            } else {
                this.validator = PersistenceManager.getInstance(as.getPuSlave())
                    .getValidatorFactory().getValidator();
            }
        }
        
        return validator;
    }
    
    protected EntityManager getEntityManager() {
        if (emDcors == null) {
            log.error("El entitymanager no pudo crearse.");
            return null;
        }
        
        try {
            return emDcors;
        } catch (Exception ex) {
            log.error("No se pudo crear el entitymanager.", ex);
            return null;
        }
        
    }   
    
    public void create(T entity) throws Exception, Exception {
        //validate(entity);
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    public void edit(T entity) throws Exception, Exception {
        //validate(entity);
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().flush();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    //XXX Ver para que sea genérico
    public List<T> findAllByActiveGeneric(boolean onlyActive) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery q = cb.createQuery();
        Root<T> root = q.from(entityClass);
        q.select(root);
        
        //q.where("((?1 = TRUE AND s.active = TRUE) OR ?2 = FALSE) ");
        
        //q.w
        
        return null;
    }
    
    public List<T> findAll() {
        return findAll(null);
    }
    
    public List<T> findAll(String[] fieldsOrderBy) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery q = cb.createQuery();
        Root<T> root = q.from(entityClass);
        q.select(root);

        // Resolviendo el order by pro varios campos, todos ascendentes :)
        List<Order> orders = null;
        if(fieldsOrderBy != null) {
            for(int i = 0; i < fieldsOrderBy.length; i++) {
                if(fieldsOrderBy[i] != null && !fieldsOrderBy[i].trim().isEmpty()) {
                    if(i == 0) orders = new ArrayList<Order>();
                    if(root.get(fieldsOrderBy[i]) != null) {
                        orders.add(cb.asc(root.get(fieldsOrderBy[i])));
                    } else {
                        log.warn("El campo {} no es atributo de la entidad {}, no se incluye en el order by.",
                                 fieldsOrderBy[i],
                                 entityClass.getSimpleName());
                    }
                }
            }
        }
        
        if(orders != null && orders.size() > 0) q.orderBy(orders);
        
        return getEntityManager().createQuery(q).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    protected void validate(T entity) throws Exception, Exception {
        Set<ConstraintViolation<T>> validationErrors = null;
        Set<ConstraintViolation<?>> constraintErrors = null;
        String debug = "\nError %s-CONSTRAINT, %s - %s: %s [%s] [%s]";  
        List<String> msgs = null;
        StringBuilder msg = new StringBuilder("");
        
        try {
            //Obteniendo los errores de validación
            validationErrors = getValidator().validate(entity);

            if(validationErrors.isEmpty()) {
                log.info(String.format("La entidad [%s] fue validada exitósamente.", entity.getClass().getSimpleName()));
            } else {
                log.error(String.format("Se presentaron errores al validar la entidad [%s].", entity.getClass().getSimpleName()));

                msgs = new ArrayList<String>();
                msg.append("Se presentaron errores al validar la entidad [")
                   .append(entity.getClass().getSimpleName()).append("].");

                int count = 0;
                for(ConstraintViolation<T> errors: validationErrors) {
                    count++;
                    ConstraintDescriptor cd = errors.getConstraintDescriptor();
                    msg.append(String.format(debug, "JPA", count, errors.getPropertyPath(), errors.getMessage(), errors.getInvalidValue(), cd.getAnnotation()));
                    msgs.add(String.format(debug, "JPA", count, errors.getPropertyPath(), errors.getMessage(), errors.getInvalidValue(), cd.getAnnotation()));
                }
                
                log.error(msg.toString());
                
                throw new Exception(msg.toString());
            }            
        } catch (Exception ex) {    
            if(ex instanceof Exception) { //Constraint-Error de base de datos
                log.error(msg.toString());
                
                throw new Exception(msg.toString());
            } else if(ex instanceof Exception) {
                throw (Exception) ex;
            } else {    
                throw new Exception(String.format("Error desconocido en validación de entidad "+ entity.getClass().getSimpleName())+" " +
                                            ex.getLocalizedMessage());
            }
        }
    }
    
    public void detached(T t) {
        getEntityManager().detach(t);
    }

    public String encript(String passwd) throws Exception{
        try {
            return AESSymetricCrypto.encriptInSHA512HEX2(passwd);
        } catch (Exception ex) {
            throw new Exception("Error al encriptar contraseña:"+ex.getLocalizedMessage());
        }
    }    
}
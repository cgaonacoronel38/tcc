/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools |Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import py.edu.columbia.tcc.exception.GDMConfigException;
import py.edu.columbia.tcc.exception.GDMEJBException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.metadata.ConstraintDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.columbia.tcc.manager.PersistenceManager;
import py.edu.columbia.tcc.manager.ResourceManager;
import py.edu.columbia.tcc.setup.AppSetup;

/**
 *
 * @author tokio
 */
public abstract class AbstractFacade<T> {

    protected final static Logger log = LoggerFactory.getLogger(AbstractFacade.class);

    @PersistenceContext(unitName = "ContentEJBPU")
    private EntityManager emMaster;

    private Class<T> entityClass;

    private Validator validator;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Validator getValidator() throws GDMConfigException, GDMEJBException, Exception {
        if (validator == null) {
            AppSetup as = ResourceManager.getInstance().getAppSetup();

            this.validator = PersistenceManager.getInstance(as.getPuMaster())
                    .getValidatorFactory().getValidator();
        }

        return validator;
    }

    protected EntityManager getEntityManager() {
        if (emMaster == null) {
            log.error("El/los entitymanagers no pudieron crearse.");

            return null;
        }

        try {
            AppSetup as = ResourceManager.getInstance().getAppSetup();

            return emMaster;
        } catch (Exception ex) {
            log.error("No se pudo crear el entitymanager.", ex);
        }

        return null;
    }

    /*
    public EntityManager getEntityManagerFactory() {
        if(em == null) {
            em = PersistenceManager.getInstance(setup.getAppSetup().getPersistenceUnitName()).getEntityManager();
            
            validator = PersistenceManager.getInstance(setup.getAppSetup().getPersistenceUnitName())
                    .getValidatorFactory().getValidator();
            
            log.debug("Se creo EM: " + setup.getAppSetup().getPersistenceUnitName() + " y el validator.");
        }
        return em; 
    }
     */
    public void create(T entity) throws ConstraintViolationException, GDMEJBException {
        try {
            //validate(entity);
            getEntityManager().persist(entity);
            getEntityManager().flush();
        } catch (Exception ex) {
            throw new GDMEJBException("Error al insertar entidad" + ex.getLocalizedMessage());
        }
    }

    public T refresh(T entity) throws ConstraintViolationException, GDMEJBException {
        try {
            getEntityManager().refresh(entity);
            return entity;
        } catch (Exception ex) {
            throw new GDMEJBException("Error al insertar entidad" + ex.getLocalizedMessage());
        }
    }

    public void edit(T entity) throws ConstraintViolationException, GDMEJBException {
        //validate(entity);
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }
    
    public T merge(T entity) throws ConstraintViolationException, GDMEJBException {
        return getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        //getEntityManager().getTransaction().begin();
        getEntityManager().merge(entity);
//        getEntityManager().flush();
        getEntityManager().remove(entity);
        //getEntityManager().merge(entity);
        getEntityManager().flush();
        //getEntityManager().getTransaction().commit();
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
        if (fieldsOrderBy != null) {
            for (int i = 0; i < fieldsOrderBy.length; i++) {
                if (fieldsOrderBy[i] != null && !fieldsOrderBy[i].trim().isEmpty()) {
                    if (i == 0) {
                        orders = new ArrayList<Order>();
                    }
                    if (root.get(fieldsOrderBy[i]) != null) {
                        orders.add(cb.asc(root.get(fieldsOrderBy[i])));
                    } else {
                        log.warn("El campo {} no es atributo de la entidad {}, no se incluye en el order by.",
                                fieldsOrderBy[i],
                                entityClass.getSimpleName());
                    }
                }
            }
        }

        if (orders != null && orders.size() > 0) {
            q.orderBy(orders);
        }

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

    protected void validate(T entity) throws ConstraintViolationException, GDMEJBException {
        Set<ConstraintViolation<T>> validationErrors = null;
        Set<ConstraintViolation<?>> constraintErrors = null;
        String debug = "\nError %s-CONSTRAINT, %s - %s: %s [%s] [%s]";
        List<String> msgs = null;
        StringBuilder msg = new StringBuilder("");

        try {
            //Obteniendo los errores de validación
            validationErrors = getValidator().validate(entity);

            if (validationErrors.isEmpty()) {
                log.info(String.format("La entidad [%s] fue validada exitósamente.", entity.getClass().getSimpleName()));
            } else {
                log.error(String.format("Se presentaron errores al validar la entidad [%s].", entity.getClass().getSimpleName()));

                msgs = new ArrayList<String>();
                msg.append("Se presentaron errores al validar la entidad [")
                        .append(entity.getClass().getSimpleName()).append("].");

                int count = 0;
                for (ConstraintViolation<T> errors : validationErrors) {
                    count++;
                    ConstraintDescriptor cd = errors.getConstraintDescriptor();
                    msg.append(String.format(debug, "JPA", count, errors.getPropertyPath(), errors.getMessage(), errors.getInvalidValue(), cd.getAnnotation()));
                    msgs.add(String.format(debug, "JPA", count, errors.getPropertyPath(), errors.getMessage(), errors.getInvalidValue(), cd.getAnnotation()));
                }

                log.error(msg.toString());

                throw new GDMEJBException(msg.toString(), msgs);
            }
        } catch (Exception ex) {
            if (ex instanceof ConstraintViolationException) { //Constraint-Error de base de datos
                ConstraintViolationException violationEx = (ConstraintViolationException) ex.getCause();
                constraintErrors = violationEx.getConstraintViolations();

                msgs = new ArrayList<String>();
                msg.append("Se presentaron errores al validar la entidad [")
                        .append(entity.getClass().getSimpleName()).append("].");

                int count = 0;
                for (ConstraintViolation<?> constraint : constraintErrors) {
                    ConstraintDescriptor desc = constraint.getConstraintDescriptor();
                    String str = String.format(debug,
                            count,
                            "SQL",
                            constraint.getPropertyPath(),
                            constraint.getMessage(),
                            constraint.getInvalidValue(),
                            desc.getAnnotation());
                    msg.append(str);
                    msgs.add(str);
                }

                log.error(msg.toString());

                throw new GDMEJBException(msg.toString(), msgs);
            } else if (ex instanceof GDMEJBException) {
                throw (GDMEJBException) ex;
            } else {
                throw new GDMEJBException(String.format("Error desconocido en validación de entidad [%s]", entity.getClass().getSimpleName()),
                        ex);
            }
        }
    }

    public void detached(T t) {
        getEntityManager().detach(t);
    }
}

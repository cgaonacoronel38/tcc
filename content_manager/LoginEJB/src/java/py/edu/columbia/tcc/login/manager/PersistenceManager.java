package py.edu.columbia.tcc.login.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase de tipo singleton encargado de encapsular toda la funcionaliad
 * con respecto a la creación, instanciación y finalización de la
 * Unidad de Persistencia, además de crear los manejadores de entidades.
 *
 * @see EntityManager
 * @see ValidatorFactory
 */
public class PersistenceManager {
    private final static Logger LOG = LoggerFactory.getLogger(PersistenceManager.class);
    
    private static PersistenceManager singleton = null;
    private static EntityManagerFactory emf = null;
    private ValidatorFactory validatorFactory = null;
    private static String persistUnitName = "";


    private PersistenceManager() {
    }

    public static PersistenceManager getInstance(String puName) {
        if(singleton == null){
            singleton = new PersistenceManager();
            emf = Persistence.createEntityManagerFactory(puName);
            persistUnitName = puName;
            LOG.info("Contenedor de persistencia inicializada, Persistence-Unit = " + puName);
        }

        return singleton;
    }

    public ValidatorFactory getValidatorFactory() {
        if(validatorFactory == null) {
            validatorFactory = javax.validation.Validation.buildDefaultValidatorFactory();
        }

        return validatorFactory;
    }

    public boolean isOpen() {
        if(emf == null) {
            return false;
        } else {
            return emf.isOpen();
        }
    }

    public EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }
    
    /**
     * Retorna la instancia del Entity manager.
     *
     * @return Retorna una instancia de un EntityManager
     */
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public void connectIfNeeded() {
        if(emf != null && !emf.isOpen()) {
            singleton = null;
            singleton = PersistenceManager.getInstance(persistUnitName);
        }
    }

    public void close() {
        if(emf != null && emf.isOpen()) emf.close();

        emf = null;
        singleton = null;
        LOG.info("Contenedor de persistencia finalizada.");
    }

    public static String getPersistUnitName() {
        return persistUnitName;
    }

    public static void setPersistUnitName(String persistUnitName) {
        PersistenceManager.persistUnitName = persistUnitName;
    }

    public static void main(String[] args){
        EntityManager em = PersistenceManager.getInstance("TCCEJB").getEntityManager();
        em.createQuery("select * from user").getResultList();
    }
}

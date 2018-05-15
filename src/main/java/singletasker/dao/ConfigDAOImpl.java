package singletasker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.util.List;

/**
 * Encapculates the implementation of the database operations on {@link ConfigEntity}.
 */
public class ConfigDAOImpl implements ConfigDAO {
    private static ConfigDAOImpl instance;
    private static DatabaseManager db = DatabaseManager.getInstance();
    private Logger logger = LoggerFactory.getLogger(ConfigDAOImpl.class);

    private ConfigDAOImpl() {}

    /**
     * Creates the ConfigDAOImpl instance or returns it if it's already created.
     * @return the instance
     */
    public static ConfigDAOImpl getInstance() {
        if (instance == null) {
            instance = new ConfigDAOImpl();
        }

        return instance;
    }

    @Override
    public ConfigEntity findByKey(String key) {
        if (key != null) {
            db.getEntityManager().getTransaction().begin();
            Query q = db.getEntityManager().createQuery("from ConfigEntity where configKey = :keyParam", ConfigEntity.class);
            q.setParameter("keyParam", key);
            List<ConfigEntity> l = q.getResultList();
            db.getEntityManager().getTransaction().commit();

            if (!l.isEmpty()) {
                logger.info("ConfigEntity successfully retrieved: " + l.get(0));
                return l.get(0);
            } else {
                logger.warn("ConfigEntity wasn't found with key: " + key);
                return null;
            }
        }
        return null;
    }

    @Override
    public void save(ConfigEntity configEntity) {
        if (findByKey(configEntity.getConfigKey()) != null) {
            db.getEntityManager().getTransaction().begin();
            db.getEntityManager().merge(configEntity);
            db.getEntityManager().getTransaction().commit();
            logger.info("ConfigEntity updated, " + configEntity);
        } else {
            db.getEntityManager().getTransaction().begin();
            db.getEntityManager().persist(configEntity);
            db.getEntityManager().getTransaction().commit();
            logger.info("ConfigEntity inserted, " + configEntity);
        }
    }
}

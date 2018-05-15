package singletasker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.util.List;

public class ConfigDAOImpl implements ConfigDAO {
    private static ConfigDAOImpl instance;
    private static DatabaseManager db = DatabaseManager.getInstance();
    private Logger logger = LoggerFactory.getLogger(ConfigDAOImpl.class);

    private ConfigDAOImpl() {}

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
                return l.get(0);
            } else {
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
        } else {
            db.getEntityManager().getTransaction().begin();
            db.getEntityManager().persist(configEntity);
            db.getEntityManager().getTransaction().commit();
        }
    }
}

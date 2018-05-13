package singletasker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseManager {

    private static DatabaseManager instance;
    private EntityManager entityManager;

    private Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    private DatabaseManager() {}

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public void connect() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("stDB");
        entityManager = entityManagerFactory.createEntityManager();
        logger.info("Connected to the database");
    }

    public boolean isConnected() {
        return entityManager != null && entityManager.isOpen();
    }

    public void disconnect() {
        if (isConnected()) {
            entityManager.close();
            logger.info("Disconnected from the database");
        }
        entityManager = null;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}

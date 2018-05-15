package singletasker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Manages the JPA database connection.
 */
public class DatabaseManager {
    /**
     * Instance.
     */
    private static DatabaseManager instance;

    /**
     * EntityManager's instance.
     */
    private EntityManager entityManager;

    /**
     * Logger user for logging.
     */
    private Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    /**
     * Disabled because we need only one instance. (singleton)
     */
    private DatabaseManager() {}

    /**
     * Creates the DatabaseManager instance or returns it if it's already created.
     * @return the instance
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Connect's to the database.
     */
    public void connect() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("stDB");
        entityManager = entityManagerFactory.createEntityManager();
        logger.info("Connected to the database");
    }

    /**
     * Checks whether we connected to the database.
     * @return true, if we
     */
    public boolean isConnected() {
        return entityManager != null && entityManager.isOpen();
    }

    /**
     * Disconnects from the database.
     */
    public void disconnect() {
        if (isConnected()) {
            entityManager.close();
            logger.info("Disconnected from the database");
        }
        entityManager = null;
    }

    /**
     * Gets our {@link EntityManager} instance.
     * @return the {@link EntityManager} instance
     */
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}

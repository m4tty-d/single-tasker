package singletasker.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Encapsulates the implementation of database operations on {@link TaskEntity}.
 */
public class TaskDAOImpl implements TaskDAO {
    /**
     * The TaskDAOImpl instance.
     */
    private static TaskDAOImpl instance;

    /**
     * {@link DatabaseManager} instance.
     */
    private static DatabaseManager db = DatabaseManager.getInstance();

    /**
     * Logger used for logging.
     */
    private Logger logger = LoggerFactory.getLogger(ConfigDAOImpl.class);

    /**
     * Disabled because of singleton.
     */
    private TaskDAOImpl() {}

    /**
     * Creates the TaskDAOImpl instance or returns it if it's already created.
     * @return the instance
     */
    public static TaskDAOImpl getInstance() {
        if (instance == null) {
            instance = new TaskDAOImpl();
        }

        return instance;
    }

    /**
     * Inserts a {@link TaskEntity} into the database.
     * @param taskEntity the {@link TaskEntity} to be inserted
     */
    @Override
    public void insert(TaskEntity taskEntity) {
        if (taskEntity != null) {
            db.getEntityManager().getTransaction().begin();
            db.getEntityManager().persist(taskEntity);
            db.getEntityManager().getTransaction().commit();
        }
    }

    /**
     * Updates a {@link TaskEntity}.
     * @param taskEntity the {@link TaskEntity} to be updated
     */
    @Override
    public void update(TaskEntity taskEntity) {
        if (taskEntity != null) {
            db.getEntityManager().getTransaction().begin();
            db.getEntityManager().merge(taskEntity);
            db.getEntityManager().getTransaction().commit();
        }
    }

    /**
     * Deletes a {@link TaskEntity} by id.
     * @param id the id
     */
    @Override
    public void delete(Long id) {
        if (id != null) {
            db.getEntityManager().getTransaction().begin();
            db.getEntityManager().createQuery("delete from TaskEntity where id = :id").setParameter("id", id).executeUpdate();
            db.getEntityManager().getTransaction().commit();
        }
    }

    /**
     * Gets a {@link TaskEntity} by id.
     * @param id the id of the wanted {@link TaskEntity}
     * @return the instance, or null if wasn't found
     */
    @Override
    public TaskEntity findById(Long id) {
        if (id != null) {
            db.getEntityManager().getTransaction().begin();
            TaskEntity t = db.getEntityManager().find(TaskEntity.class, id);
            return t;
        }
        return null;
    }

    /**
     * Gets all {@link TaskEntity}'s.
     * @return a list of the rows
     */
    @Override
    public List<TaskEntity> findAll() {
        return db.getEntityManager().createQuery("from TaskEntity", TaskEntity.class).getResultList();
    }
}

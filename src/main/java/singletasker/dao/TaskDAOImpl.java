package singletasker.dao;

import singletasker.models.Task;

import java.util.List;

public class TaskDAOImpl implements TaskDAO {

    private static TaskDAOImpl instance;
    private static DatabaseManager db = DatabaseManager.getInstance();

    private TaskDAOImpl() {}

    public static TaskDAOImpl getInstance() {
        if (instance == null) {
            instance = new TaskDAOImpl();
        }

        return instance;
    }

    @Override
    public boolean insert(Task task) {
        if (task != null) {
            db.getEntityManager().getTransaction().begin();
            db.getEntityManager().persist(task);
            db.getEntityManager().getTransaction().commit();

            return true;
        }

        return false;
    }

    @Override
    public boolean update(Task task) {
        if (task != null) {
            db.getEntityManager().getTransaction().begin();
            db.getEntityManager().merge(task);
            db.getEntityManager().getTransaction().commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Task task) {
        if (task != null) {
            db.getEntityManager().getTransaction().begin();
            db.getEntityManager().remove(task);
            db.getEntityManager().getTransaction().commit();
        }
        return false;
    }

    @Override
    public Task findById(Long id) {
        if (id != null) {
            db.getEntityManager().getTransaction().begin();
            Task t = db.getEntityManager().find(Task.class, id);
            return t;
        }
        return null;
    }

    @Override
    public List<Task> findAll() {
        return db.getEntityManager().createQuery("from Task", Task.class).getResultList();
    }
}

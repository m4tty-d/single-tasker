package singletasker.dao;

import singletasker.models.User;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl instance;
    private static DatabaseManager db = DatabaseManager.getInstance();

    private UserDAOImpl() {}

    public static UserDAOImpl getInstance() {
        if (instance == null) {
            instance = new UserDAOImpl();
        }

        return instance;
    }

    @Override
    public void insertOrUpdate(User user) {
        if (this.findById(user.getId()) != null) {
            db.getEntityManager().getTransaction().begin();
            db.getEntityManager().merge(user);
            db.getEntityManager().getTransaction().commit();
        } else {
            db.getEntityManager().getTransaction().begin();
            db.getEntityManager().persist(user);
            db.getEntityManager().getTransaction().commit();
        }
    }

    @Override
    public User findById(Long id) {
        if (id != null) {
            db.getEntityManager().getTransaction().begin();
            User u = db.getEntityManager().find(User.class, id);
            return u;
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return db.getEntityManager().createQuery("from User", User.class).getResultList();
    }
}

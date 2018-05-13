package singletasker.controllers;

import singletasker.dao.DatabaseManager;
import singletasker.dao.UserDAOImpl;
import singletasker.models.User;

import java.util.List;

public class UserController {
    private static UserController instance;
    private User userInstance;
    private UserDAOImpl dao = UserDAOImpl.getInstance();

    private UserController() {
        List<User> users = dao.findAll();
        if (users.size() == 1) {
            userInstance = users.get(0);
        }
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }

        return instance;
    }

    public void update(User user) {
        userInstance = user;
        dao.insertOrUpdate(user);
    }
}

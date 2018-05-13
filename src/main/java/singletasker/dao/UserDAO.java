package singletasker.dao;

import singletasker.models.User;

import java.util.List;

public interface UserDAO {
    void insertOrUpdate(User u);
    User findById(Long id);
    List<User> findAll();
}

package singletasker.dao;

import java.util.List;

public interface DAO<T, S> {
    void insert(T t);
    void update(T t);
    void delete(S id);
    T findById(S id);
    List<T> findAll();
}

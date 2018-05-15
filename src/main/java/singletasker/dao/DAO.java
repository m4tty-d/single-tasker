package singletasker.dao;

import java.util.List;

/**
 * Generic DAO interface.
 * @param <T> entity class
 * @param <S> type of the id field
 */
public interface DAO<T, S> {
    void insert(T t);
    void update(T t);
    void delete(S id);
    T findById(S id);
    List<T> findAll();
}

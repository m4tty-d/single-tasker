package singletasker.dao;


import java.util.List;

public interface DAO<T, S> {
    boolean insert(T t);
    boolean update(T t);
    boolean delete(T t);
    T findById(S id);
    List<T> findAll();
}

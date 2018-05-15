package singletasker.dao;

import java.util.List;

/**
 * Generic DAO interface.
 * @param <T> entity class
 * @param <S> type of the id field
 */
public interface DAO<T, S> {
    /**
     * Inserts a {@link T} entity into the database.
     * @param t instance to be inserted
     * @return the inserted instance on success, null otherwise
     */
    T insert(T t);

    /**
     * Updates a {@link T} entity in the database.
     * @param t instance to be updated
     */
    void update(T t);

    /**
     * Deletes a {@link T} instance from the database by it's id.
     * @param id id of the instance to be removed
     */
    void delete(S id);

    /**
     * Gets a {@link T} entity from the database by id.
     * @param id id of the entity
     * @return {@link} T instance on success, null otherwise
     */
    T findById(S id);

    /**
     * Gets all {@link T} entities from the database.
     * @return a list of the entities
     */
    List<T> findAll();
}

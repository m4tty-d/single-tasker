package singletasker.dao;

/**
 * Encapculates the database operations on {@link ConfigEntity}.
 */
public interface ConfigDAO {
    ConfigEntity findByKey(String key);
    void save(ConfigEntity configEntity);
}

package singletasker.dao;

/**
 * Encapculates the database operations on {@link ConfigEntity}.
 */
public interface ConfigDAO {
    /**
     * Gets the {@link ConfigEntity} by key.
     * @param key key of the entity which we looking for
     * @return {@link ConfigEntity} instance on success, null otherwise
     */
    ConfigEntity findByKey(String key);

    /**
     * Saves a a config entity. If it's already in the database it's being updated, otherwise inserted.
     * @param configEntity the config to be saved
     */
    void save(ConfigEntity configEntity);
}

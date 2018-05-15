package singletasker.dao;

public interface ConfigDAO {
    ConfigEntity findByKey(String key);
    void save(ConfigEntity configEntity);
}

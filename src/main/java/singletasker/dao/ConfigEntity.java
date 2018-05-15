package singletasker.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Basically a key value store, which represents the configs.
 */
@Entity
@Table(name="CONFIG")
public class ConfigEntity {
    /**
     * Key of the config entity.
     */
    @Id
    private String configKey;

    /**
     * Value of the config entity.
     */
    private String configValue;

    /**
     * Default contructor.
     */
    public ConfigEntity() {}

    /**
     * Contructor which sets the fields.
     * @param configKey key to be set
     * @param configValue value to be set
     */
    public ConfigEntity(String configKey, String configValue) {
        this.configKey = configKey;
        this.configValue = configValue;
    }

    /**
     * Gets the key of the entity.
     * @return key of the config
     */
    public String getConfigKey() {
        return configKey;
    }

    /**
     * Sets the key of the entity.
     * @param name key to be set
     */
    public void setConfigKey(String name) {
        this.configKey = name;
    }

    /**
     * Gets the entity's value.
     * @return the value of the config
     */
    public String getConfigValue() {
        return configValue;
    }

    /**
     * Sets the entity's value.
     * @param value the value to be set
     */
    public void setConfigValue(String value) {
        this.configValue = value;
    }

    @Override
    public String toString() {
        return "ConfigEntity{" +
                ", configKey='" + configKey + '\'' +
                ", configValue='" + configValue + '\'' +
                '}';
    }
}

package singletasker.dao;

import javax.persistence.*;

@Entity
@Table(name="CONFIG")
public class ConfigEntity {
    @Id
    @GeneratedValue
    private Long Id;

    private String configKey;

    private String configValue;

    public ConfigEntity() {}

    public ConfigEntity(String configKey, String configValue) {
        this.configKey = configKey;
        this.configValue = configValue;
    }

    public Long getId() {
        return Id;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String name) {
        this.configKey = name;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String value) {
        this.configValue = value;
    }
}

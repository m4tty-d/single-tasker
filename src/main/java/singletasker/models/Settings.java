package singletasker.models;

import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Settings {
    @Id
    @GeneratedValue
    private Long id;
    private int pomodoroTime = 25;
    private int shortBreakTime = 5;
    private int longBreakTime = 15;

    public Settings() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPomodoroTime() {
        return pomodoroTime;
    }

    public void setPomodoroTime(int pomodoroTime) {
        this.pomodoroTime = pomodoroTime;
    }

    public int getShortBreakTime() {
        return shortBreakTime;
    }

    public void setShortBreakTime(int shortBreakTime) {
        this.shortBreakTime = shortBreakTime;
    }

    public int getLongBreakTime() {
        return longBreakTime;
    }

    public void setLongBreakTime(int longBreakTime) {
        this.longBreakTime = longBreakTime;
    }
}

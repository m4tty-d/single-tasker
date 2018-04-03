package models;

public class Task {

    private String name;
    private int difficultyLevel = 0;
    private int pomodoroCount = 0;
    private TaskState currentState;

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getPomodoroCount() {
        return pomodoroCount;
    }

    public void setPomodoroCount(int pomodoroCount) {
        this.pomodoroCount = pomodoroCount;
    }

    public TaskState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(TaskState currentState) {
        this.currentState = currentState;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", pomodoroCount=" + pomodoroCount +
                ", currentState=" + currentState +
                '}';
    }
}

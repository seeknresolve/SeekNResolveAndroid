package org.seeknresolve.android.model;

public class Bug {
    private String tag;
    private String name;
    private String description;
    private Project project;
    private State state;
    private Priority priority;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public enum State {
        NEW,
        REJECTED,
        IN_PROGRESS,
        READY_TO_TEST,
        REOPENED,
        STOPPED,
        CLOSED
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        CRITICAL
    }
}

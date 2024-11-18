package task;

import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Status status;
    protected int id;

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, Status status) {
        this.status = status;
        this.description = description;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (name != null) {
            hash = name.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return "name:" + name + '\'' +
                "description:" + description + '\'' +
                "status:" + status + '\'' +
                "id:" + id;
    }
}

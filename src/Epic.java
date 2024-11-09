import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskId = new ArrayList<>();
    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
        this.status = Status.NEW;
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        this.status = Status.NEW;
    }

    public ArrayList<Integer> getSubTaskId() {
        return subtaskId;
    }

    public void addSubtaskId(int index) {
        subtaskId.add(index);
    }

    public void removeSubtaskId(int index) {
        subtaskId.remove(index);
    }

    public void deleteAllSubtaskId() {
        subtaskId.clear();
    }
}

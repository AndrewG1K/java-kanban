package manager;
import task.Task;
import java.util.List;

interface HistoryManager {
    public void addToHistory(Task task);
    public List<Task> getHistory();
}

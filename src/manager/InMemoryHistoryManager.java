package manager;
import task.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final List<Task> history = new ArrayList<>();

    @Override
    public void addToHistory(Task task) {
        if(history.size()<=10) {
            history.add(task);
        }else{
            history.removeFirst();
            history.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}

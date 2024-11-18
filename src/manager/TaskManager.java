package manager;
import task.*;
import java.util.List;

public interface TaskManager {

    public List<SubTask> getSubTasks();
    public List<Epic> getEpics();
    public List<Task> getTasks();

    public int addNewTask(Task task);
    public int addNewEpic(Epic epic);
    public int addNewSubtask(SubTask subTask);

    public List<Task> allTasks();
    public List<Epic> allEpics();
    public List<SubTask> allSubtasks();

    public void deleteAllTasks();
    public void deleteAllEpicsAndSubTasks();

    public Task getTaskById(int index);
    public Epic getEpicById(int index);
    public SubTask getSubtaskById(int index);
    public List<SubTask> getSubtaskByEpicId(int epicId);

    public void deleteTaskById(int index);
    public void deleteEpicById(int index);
    public void deleteSubtaskById(int index);

    public void updateTask(Task task);
    public void updateEpic(Epic epic);
    public void updateSubtask(SubTask subTask);

    public List<SubTask> getSubtasksFromEpic(int epicId);
    public List<Task> getHistory();
}

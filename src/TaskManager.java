import java.util.ArrayList;

public interface TaskManager {

    public ArrayList<SubTask> getSubTasks();
    public ArrayList<Epic> getEpics();
    public ArrayList<Task> getTasks();

    public int addNewTask(Task task);
    public int addNewEpic(Epic epic);
    public int addNewSubtask(SubTask subTask);

    public ArrayList<Task> allTasks();
    public ArrayList<Epic> allEpics();
    public ArrayList<SubTask> allSubtasks();

    public void deleteAllTasks();
    public void deleteAllEpicsAndSubTasks();

    public Task getTaskById(int index);
    public Epic getEpicById(int index);
    public SubTask getSubtaskById(int index);
    public ArrayList<SubTask> getSubtaskByEpicId(int epicId);

    public void deleteTaskById(int index);
    public void deleteEpicById(int index);
    public void deleteSubtaskById(int index);

    public void updateTask(Task task);
    public void updateEpic(Epic epic);
    public void updateSubtask(SubTask subTask);

    public ArrayList<SubTask> getSubtasksFromEpic(int epicId);
}

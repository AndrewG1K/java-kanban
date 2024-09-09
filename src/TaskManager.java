import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private int id = 0;

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }
    //Добавляем задачи по категориям
    public int addNewTask(Task task) {
        id += 1;
        tasks.put(id, task);
        return id;
    }

    public int addNewEpic(Epic epic) {
        id += 1;
        epics.put(id, epic);
        return id;
    }

    public int addNewSubtask(SubTask subTask) {  //сделать список id сабтасков для одного эпика
        for (int index : epics.keySet()){
            if(subTask.getEpicId() == index){
                id += 1;
                subTasks.put(id, subTask);
            }
        }
        return id;
    }

    //Получение списка задач по категориям

    public ArrayList<Task> allTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        for (Task task : tasks.values()) {
            tasksList.add(task);
        }
        return tasksList;
    }

    public ArrayList<Epic> allEpics() {
        ArrayList<Epic> epicsList = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicsList.add(epic);
        }
        return epicsList;
    }

    public ArrayList<SubTask> allSubtasks() {
        ArrayList<SubTask> subTasksList = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            subTasksList.add(subTask);
        }
        return subTasksList;
    }

    //Удаление всех задач по категориям

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpicsAndSubTasks() { //Если удаляется эпик, удаляются и все сабтаски, которые в него входят
        subTasks.clear();
        epics.clear();
    }

    //Получение задачи по идентификатору

    public Task getTaskById(int index) {
        Task task = null;
        if (tasks.isEmpty()) {
            return task;
        }
        for (int i : tasks.keySet()) {
            if (i == index) {
                task = tasks.get(index);
            } else {
                return task;
            }
        }
        return task;
    }

    public Epic getEpicById(int index) {
        Epic epic = null;
        if (epics.isEmpty()) {
            return epic;
        }
        for (int i : epics.keySet()) {
            if (i == index) {
                epic = epics.get(index);
            } else {
                return epic;
            }
        }
        return epic;
    }

    public SubTask getSubtaskByEpicId(int epicId) {
        SubTask subTask = null;
        if (subTasks.isEmpty()){
            return subTask;
        }
        for (int i : subTasks.keySet()) {
            if (i == epicId) {
                subTask = subTasks.get(epicId);
            } else {
                return subTask;
            }
        }
        return subTask;
    }

    //Удаление задачи по идентификатору

    public void deleteTaskById(int index) {
        if (tasks.isEmpty()){
            return;
        }
        for (int i : subTasks.keySet()) {
            if(i == index) {
                subTasks.remove(index);
            }
        }
    }

    public void deleteEpicById(int index) {
        if (epics.isEmpty()){
            return;
        }
        for (int i : epics.keySet()) {
            if(i == index) {
                epics.remove(index);
            }
        }
    }

    public void deleteSubtaskById(int epicId) {
        if (subTasks.isEmpty()){
            return;
        }
        for (int i : subTasks.keySet()) {
            if(i == epicId) {
                subTasks.remove(epicId);
            }
        }
    }

    //Обновление задачи

    public void updateTask(int index, Task task) {
        if (tasks.isEmpty()){
            return;
        }
        for (int i : tasks.keySet()) {
            if (i == index) {
                tasks.remove(index);
                tasks.put(index, task);
            } else {
                return;
            }
        }
    }

    public Status updateEpicStatus(int epicId) {
        Status status = null;
        if (epics.isEmpty()){
            return status;
        }
        for (int i : subTasks.keySet()) {
            SubTask subTask = subTasks.get(i);
            if (epicId == subTask.getEpicId()) {
                if (subTask.getStatus().equals(Status.NEW)){
                    status = Status.NEW;
                } else if (subTask.getStatus().equals(Status.DONE)) {
                    status = Status.DONE;
                }else{
                    return Status.IN_PROGRESS;
                }
            }
        }
        return status;
    }

    public void updateSubtask(int subtaskId, SubTask subTask) {
        if (subTasks.isEmpty()) {
            return;
        }
        for (int i : subTasks.keySet()) {
            if (i == subtaskId) {
                subTasks.put(subtaskId, subTask);
                subTask = subTasks.get(i);
                updateEpicStatus(subTask.getEpicId());
            }
        }
    }

    //Получение сабтасков определенного эпика
    public ArrayList<SubTask> getSubtasksFromEpic(int epicId) {
        ArrayList<SubTask> subtasksFromEpic = new ArrayList<>();
        for (int i : subTasks.keySet()){
            SubTask subTask = subTasks.get(i);
            if (epicId == subTask.getEpicId()) {
                subtasksFromEpic.add(subTasks.get(i));
            }
        }
        return subtasksFromEpic;
    }
}

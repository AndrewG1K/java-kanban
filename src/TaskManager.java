import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private int id = 0;
    private Epic epic;
    private SubTask subTask;

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
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    public int addNewSubtask(SubTask subTask) {
        if (epics.get(subTask.getEpicId()) != null){
            subTask.setId(id += 1);
            subTasks.put(id, subTask);
            epic = epics.get(subTask.getEpicId());
            epic.addSubtaskId(id);
            updateEpicStatus(subTask.getEpicId());
            return id;
        }
        return -1;
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

    public void deleteAllEpicsAndSubTasks() {
        subTasks.clear();
        epics.clear();
        epic.deleteAllSubtaskId();
    }

    //Получение задачи по идентификатору

    public Task getTaskById(int index) {
        Task task = tasks.get(index);
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
        Epic epic = epics.get(index);
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
        SubTask subTask = subTasks.get(epicId);
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
        tasks.remove(index);
    }

    public void deleteEpicById(int index) {
        if (epics.isEmpty()){
            return;
        }
        for (int key : subTasks.keySet()) {
            subTask = subTasks.get(key);
            if (index == subTask.getEpicId()) {
                subTasks.remove(key);
                epic.removeSubtaskId(key);
            }
        }
        epics.remove(index);
    }

    public void deleteSubtaskById(int index) {
        if (subTasks.isEmpty()){
            return;
        }
        subTask = subTasks.get(index);
        epic.removeSubtaskId(index);
        updateEpicStatus(subTask.getEpicId());
        subTasks.remove(index);
    }

    //Обновление задачи

    public void updateTask(Task task) {
        if (tasks.isEmpty()) {
            return;
        }else if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    private void updateEpicStatus(int epicId) {
        ArrayList<Integer> subtasksIds = new ArrayList<>();
        Status status;
        int newStatusCount = 0;
        int doneStatusCount = 0;
        if (subTasks.isEmpty()){
            epic.setStatus(Status.NEW);
            return;
        }
        for (SubTask subtask : subTasks.values()) {
            if(subtask.getEpicId() == epicId){
                subtasksIds.add(subtask.getId());
                status = subtask.getStatus();
                switch(status) {
                    case NEW:
                        newStatusCount+=1;
                        break;
                    case DONE:
                        doneStatusCount+=1;
                        break;
                    case null, default:
                        break;
                }
            }
        }
        if(subtasksIds.size() == newStatusCount){
            epic.setStatus(Status.NEW);
        }else if(subtasksIds.size() == doneStatusCount){
            epic.setStatus(Status.DONE);
        }else{
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    public void updateSubtask(int subtaskId, SubTask subTask) {
        epic = epics.get(subTask.getEpicId());
        if (subTasks.isEmpty() && epic.getSubTaskId().contains(subtaskId)) {
            return;
        }else if (epics.get(subTask.getEpicId()) != null){
            subTasks.put(subtaskId, subTask);
            updateEpicStatus(subTask.getEpicId());
        }
    }

    //Получение сабтасков определенного эпика
    public ArrayList<SubTask> getSubtasksFromEpic(int epicId) {
        ArrayList<SubTask> subtasksFromEpic = new ArrayList<>();
        epic = epics.get(epicId);
        ArrayList<Integer> ids = epic.getSubTaskId();
        for (int i : ids){
            SubTask subTask = subTasks.get(i);
            if (epicId == subTask.getEpicId()) {
                subtasksFromEpic.add(subTasks.get(i));
            }
        }
        return subtasksFromEpic;
    }
}

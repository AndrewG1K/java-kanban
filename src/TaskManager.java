import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private int id = 0;

    public ArrayList<SubTask> getSubTasks() {
        ArrayList<SubTask> subsList = new ArrayList<>();
        for(SubTask subTask : subTasks.values()){
            subsList.add(subTask);
        }
        return subsList;
    }

    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epicsList = new ArrayList<>();
        for(Epic epic : epics.values()){
            epicsList.add(epic);
        }
        return epicsList;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        for(Task task : tasks.values()){
            tasksList.add(task);
        }
        return tasksList;
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
            Epic epic = epics.get(subTask.getEpicId());
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
        deleteAllSubTasks();
        for (Epic epic : epics.values()){
            epic.deleteAllSubtaskId();
            updateEpicStatus(epic.getId());
        }
    }

    private void deleteAllSubTasks() {
        subTasks.clear();
    }

    //Получение задачи по идентификатору

    public Task getTaskById(int index) {
        Task task = tasks.get(index);
        return task;
    }

    public Epic getEpicById(int index) {
        Epic epic = epics.get(index);
        return epic;
    }

    public SubTask getSubtaskByEpicId(int epicId) {
        SubTask subTask = subTasks.get(epicId);
        return subTask;
    }

    //Удаление задачи по идентификатору

    public void deleteTaskById(int index) {
        tasks.remove(index);
    }

    public void deleteEpicById(int index) {
        Epic epic = epics.get(index);
        if(epic != null) {
            for (int subtaskId : epic.getSubTaskId()){
                subTasks.remove(subtaskId);
            }
            epics.remove(index);
        }
    }

    public void deleteSubtaskById(int index) {
        if (subTasks.isEmpty()){
            return;
        }
        SubTask subTask = subTasks.get(index);
        if (subTask != null) {
            Epic epic = epics.get(subTask.getEpicId());
            epic.removeSubtaskId(index);
            updateEpicStatus(subTask.getEpicId());
            subTasks.remove(index);
        }
    }

    //Обновление задачи

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subTasksIds = epic.getSubTaskId();
        Status status;
        int newStatusCount = 0;
        int doneStatusCount = 0;
        if (subTasksIds.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        for (int subTaskId : epic.getSubTaskId()) {
            SubTask subTask = subTasks.get(subTaskId);
            status = subTask.getStatus();
            switch (status) {
                case NEW:
                    newStatusCount += 1;
                    break;
                case DONE:
                    doneStatusCount += 1;
                    break;
                case null, default:
                    break;
            }
        }
        if(subTasksIds.size() == newStatusCount){
            epic.setStatus(Status.NEW);
        }else if(subTasksIds.size() == doneStatusCount){
            epic.setStatus(Status.DONE);
        }else{
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    public void updateSubtask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epics.get(subTask.getEpicId()) != null && epic.getSubTaskId().contains(subTask.getId())){
            subTasks.put(subTask.getId(), subTask);
            updateEpicStatus(subTask.getEpicId());
        }
    }

    //Получение сабтасков определенного эпика
    public ArrayList<SubTask> getSubtasksFromEpic(int epicId) {
        ArrayList<SubTask> subtasksFromEpic = new ArrayList<>();
        Epic epic = epics.get(epicId);
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

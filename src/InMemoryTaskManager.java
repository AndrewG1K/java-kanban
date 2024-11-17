import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private int id = 0;
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

    @Override
    public ArrayList<SubTask> getSubTasks() {
        ArrayList<SubTask> subsList = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            subsList.add(subTask);
        }
        return subsList;
    }
    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epicsList = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicsList.add(epic);
        }
        return epicsList;
    }
    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        for (Task task : tasks.values()) {
            tasksList.add(task);
        }
        return tasksList;
    }

    //Добавляем задачи по категориям
    @Override
    public int addNewTask(Task task) {
        id += 1;
        tasks.put(id, task);
        return id;
    }
    @Override
    public int addNewEpic(Epic epic) {
        id += 1;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }
    @Override
    public int addNewSubtask(SubTask subTask) {
        if (epics.get(subTask.getEpicId()) != null) {
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
    @Override
    public ArrayList<Task> allTasks() {
        ArrayList<Task> tasksList = new ArrayList<>();
        for (Task task : tasks.values()) {
            tasksList.add(task);
        }
        return tasksList;
    }
    @Override
    public ArrayList<Epic> allEpics() {
        ArrayList<Epic> epicsList = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicsList.add(epic);
        }
        return epicsList;
    }
    @Override
    public ArrayList<SubTask> allSubtasks() {
        ArrayList<SubTask> subTasksList = new ArrayList<>();
        for (SubTask subTask : subTasks.values()) {
            subTasksList.add(subTask);
        }
        return subTasksList;
    }

    //Удаление всех задач по категориям
    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }
    @Override
    public void deleteAllEpicsAndSubTasks() {
        subTasks.clear();
        epics.clear();
    }
    private void deleteAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtaskId();
            updateEpicStatus(epic.getId());
        }
    }

    //Получение задачи по идентификатору
    @Override
    public Task getTaskById(int index) {
        if(tasks.get(index) != null) {
            historyManager.addToHistory(tasks.get(index));
        }
        return tasks.get(index);
    }
    @Override
    public Epic getEpicById(int index) {
        if(epics.get(index) != null) {
            historyManager.addToHistory(epics.get(index));
        }
        return epics.get(index);
    }
    @Override
    public SubTask getSubtaskById(int index) {
        if(subTasks.get(index) != null) {
            historyManager.addToHistory(subTasks.get(index));
        }
        return subTasks.get(index);
    }
    @Override
    public ArrayList<SubTask> getSubtaskByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<SubTask> subTasksList = new ArrayList<>();
        if (epic != null) {
            for (int subTaskId : epic.getSubTaskId()) {
                subTasksList.add(subTasks.get(subTaskId));
            }
        }
        return subTasksList;
    }

    //Удаление задачи по идентификатору
    @Override
    public void deleteTaskById(int index) {
        tasks.remove(index);
    }
    @Override
    public void deleteEpicById(int index) {
        Epic epic = epics.get(index);
        if (epic != null) {
            for (int subtaskId : epic.getSubTaskId()) {
                subTasks.remove(subtaskId);
            }
            epics.remove(index);
        }
    }
    @Override
    public void deleteSubtaskById(int index) {
        if (subTasks.isEmpty()) {
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
    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }
    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateEpicStatus(epic.getId());
        }
    }
    @Override
    public void updateSubtask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epics.get(subTask.getEpicId()) != null && epic.getSubTaskId().contains(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            updateEpicStatus(subTask.getEpicId());
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
        if (subTasksIds.size() == newStatusCount) {
            epic.setStatus(Status.NEW);
        } else if (subTasksIds.size() == doneStatusCount) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    //Получение сабтасков определенного эпика
    public ArrayList<SubTask> getSubtasksFromEpic(int epicId) {
        ArrayList<SubTask> subtasksFromEpic = new ArrayList<>();
        Epic epic = epics.get(epicId);
        ArrayList<Integer> ids = epic.getSubTaskId();
        for (int i : ids) {
            SubTask subTask = subTasks.get(i);
            if (epicId == subTask.getEpicId()) {
                subtasksFromEpic.add(subTasks.get(i));
            }
        }
        return subtasksFromEpic;
    }

}

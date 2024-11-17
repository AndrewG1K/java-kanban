import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private final InMemoryTaskManager manager = new InMemoryTaskManager();
    private final Task task1 = new Task(1, "taskName1", "taskDescr1", Status.NEW);
    private final Task task2 = new Task(2, "taskName2", "taskDescr2", Status.IN_PROGRESS);
    private final Task task3 = new Task(3, "taskName3", "taskDescr3", Status.NEW);

    private final Epic epic1 = new Epic(1, "epicName1", "epicDescr1", Status.NEW);
    private final Epic epic2 = new Epic(2, "epicName2", "epicDescr2", Status.NEW);
    private final Epic epic3 = new Epic(3, "epicName3", "epicDescr3", Status.NEW);

    private final SubTask subTasks1 = new SubTask(1, "subTaskName1", "subTaskDescr1", Status.NEW, epic1.getId());
    private final SubTask subTasks2 = new SubTask(2, "subTaskName2", "subTaskDescr2", Status.IN_PROGRESS,epic1.getId());
    private final SubTask subTasks3 = new SubTask(3, "subTaskName3", "subTaskDescr3", Status.NEW, epic1.getId());

    @BeforeEach
    void setUp(){
    }

    @Test
    void addeNewTaskAndGetById() {
        Task taskToAdd = new Task(1, "taskName1", "taskDescr1", Status.NEW);
        manager.addNewTask(taskToAdd);
        assertEquals(task1, manager.getTaskById(1), "Tasks equal");
    }

    @Test
    void addNewEpicAndGetById() {
        Epic epicToAdd = new Epic(1, "epicName1", "epicDescr1", Status.NEW);
        manager.addNewEpic(epicToAdd);
        assertEquals(epic1, manager.getEpicById(1), "Epics equal");
    }

    @Test
    void addNewSubtaskAndGetById() {
        SubTask taskToAdd = new SubTask(1, "subTaskName1", "subTaskDescr1", Status.NEW, epic1.getId());
        manager.addNewTask(taskToAdd);
        assertEquals(subTasks1, manager.getTaskById(1), "SubTasks equal");
    }

    @Test
    void allTasks() {
        List<Task> tasks = new ArrayList<>();
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        assertEquals(tasks, manager.allTasks());
    }

    @Test
    void allEpics() {
        List<Epic> epics = new ArrayList<>();
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        manager.addNewEpic(epic3);
        epics.add(epic1);
        epics.add(epic2);
        epics.add(epic3);
        assertEquals(epics, manager.allEpics());
    }

    @Test
    void allSubtasks() {
        List<Task> subTasks = new ArrayList<>();
        manager.addNewEpic(epic1);
        manager.addNewSubtask(subTasks1);
        manager.addNewSubtask(subTasks2);
        manager.addNewSubtask(subTasks3);
        subTasks.add(subTasks1);
        subTasks.add(subTasks2);
        subTasks.add(subTasks3);
        assertEquals(subTasks, manager.allSubtasks());
    }

    @Test
    void deleteAllTasks() {
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);
        manager.deleteAllTasks();
        assertTrue(manager.allTasks().isEmpty());
    }

    @Test
    void deleteAllEpicsAndSubTasks() {
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        manager.addNewEpic(epic3);
        manager.addNewSubtask(subTasks1);
        manager.addNewSubtask(subTasks2);
        manager.addNewSubtask(subTasks3);
        manager.deleteAllEpicsAndSubTasks();
        assertTrue(manager.allEpics().isEmpty());
    }

    @Test
    void getSubtaskByEpicId() {
        ArrayList<Task> subTasks = new ArrayList<>();
        manager.addNewEpic(epic1);
        manager.addNewSubtask(subTasks1);
        manager.addNewSubtask(subTasks2);
        manager.addNewSubtask(subTasks3);
        subTasks.add(subTasks1);
        subTasks.add(subTasks2);
        subTasks.add(subTasks3);
        assertEquals(subTasks, manager.getSubtaskByEpicId(epic1.getId()));

    }

    @Test
    void deleteTaskById() {
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);
        manager.deleteTaskById(task2.getId());
        assertEquals(2, manager.allTasks().size());
    }

    @Test
    void deleteEpicById() {
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        manager.addNewEpic(epic3);
        manager.addNewSubtask(subTasks1);
        manager.addNewSubtask(subTasks2);
        manager.deleteEpicById(epic1.getId());
        assertEquals(2, manager.allEpics().size());
    }

    @Test
    void deleteSubtaskById() {
        manager.addNewEpic(epic1);
        manager.addNewSubtask(subTasks1);
        manager.addNewSubtask(subTasks2);
        manager.addNewSubtask(subTasks3);
        manager.deleteSubtaskById(subTasks1.getId());
        assertEquals(2, manager.allSubtasks().size());
    }

    @Test
    void updateTask() {
        Task newTask = new Task(1, "newTask1", "newTask1Desc", Status.IN_PROGRESS);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.updateTask(newTask);
        assertEquals(newTask, manager.getTaskById(newTask.getId()));
    }

    @Test
    void updateEpic() {
        Epic newEpic = new Epic(1, "newEpic1Name", "newEpic1Descr", Status.NEW);
        manager.addNewEpic(epic1);
        manager.updateEpic(newEpic);
        assertEquals(newEpic, manager.getEpicById(newEpic.getId()));
    }

    @Test
    void updateSubtask() {
        SubTask newSubTask = new SubTask(2, "subTaskName2", "subTaskDescr2", Status.IN_PROGRESS,epic1.getId());
        manager.addNewEpic(epic1);
        manager.addNewSubtask(subTasks1);
        manager.addNewSubtask(subTasks3);
        Status status1 = epic1.getStatus();
        manager.addNewSubtask(newSubTask);
        Status status2 = epic1.getStatus();
        assertEquals(newSubTask, manager.getSubtaskById(newSubTask.getId()));
        assertNotEquals(status1, status2);
    }

    @Test
    void getSubtasksFromEpic() {
        manager.addNewEpic(epic1);
        manager.addNewSubtask(subTasks1);
        manager.addNewSubtask(subTasks2);
        manager.addNewSubtask(subTasks3);
        manager.getSubtasksFromEpic(epic1.getId());
        assertEquals(3, manager.allSubtasks().size());
    }
}
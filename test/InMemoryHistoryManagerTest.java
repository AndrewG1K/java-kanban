import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import task.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    TaskManager manager = Managers.getDefault();
    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    private final Task task1 = new Task(1, "taskName1", "taskDescr1", Status.NEW);
    private final Task task2 = new Task(2, "taskName2", "taskDescr2", Status.IN_PROGRESS);

    private final Epic epic1 = new Epic(1, "epicName1", "epicDescr1", Status.NEW);
    private final Epic epic2 = new Epic(2, "epicName2", "epicDescr2", Status.NEW);

    @Test
    void addHistory() {
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        manager.getTaskById(task1.getId());
        manager.getTaskById(task2.getId());
        manager.getEpicById(epic1.getId());
        manager.getEpicById(epic2.getId());
        assertEquals(4, manager.getHistory().size());
    }

    @Test
    void getHistory() {
        ArrayList<Task> list = new ArrayList<>();
        list.add(task1);
        list.add(task2);
        list.add(epic1);
        list.add(epic2);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        manager.getTaskById(task1.getId());
        manager.getTaskById(task2.getId());
        manager.getEpicById(epic1.getId());
        manager.getEpicById(epic2.getId());
        assertEquals(list, manager.getHistory());

    }
}
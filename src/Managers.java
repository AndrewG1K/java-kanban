import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class Managers {
    public TaskManager getDefault() {
        TaskManager taskManager = new TaskManager() {
            @Override
            public ArrayList<SubTask> getSubTasks() {
                return null;
            }

            @Override
            public ArrayList<Epic> getEpics() {
                return null;
            }

            @Override
            public ArrayList<Task> getTasks() {
                return null;
            }

            @Override
            public int addNewTask(Task task) {
                return 0;
            }

            @Override
            public int addNewEpic(Epic epic) {
                return 0;
            }

            @Override
            public int addNewSubtask(SubTask subTask) {
                return 0;
            }

            @Override
            public ArrayList<Task> allTasks() {
                return null;
            }

            @Override
            public ArrayList<Epic> allEpics() {
                return null;
            }

            @Override
            public ArrayList<SubTask> allSubtasks() {
                return null;
            }

            @Override
            public void deleteAllTasks() {

            }

            @Override
            public void deleteAllEpicsAndSubTasks() {

            }

            @Override
            public Task getTaskById(int index) {
                return null;
            }

            @Override
            public Epic getEpicById(int index) {
                return null;
            }

            @Override
            public SubTask getSubtaskById(int index) {
                return null;
            }

            @Override
            public ArrayList<SubTask> getSubtaskByEpicId(int epicId) {
                return null;
            }

            @Override
            public void deleteTaskById(int index) {

            }

            @Override
            public void deleteEpicById(int index) {

            }

            @Override
            public void deleteSubtaskById(int index) {

            }

            @Override
            public void updateTask(Task task) {

            }

            @Override
            public void updateEpic(Epic epic) {

            }

            @Override
            public void updateSubtask(SubTask subTask) {

            }

            @Override
            public ArrayList<SubTask> getSubtasksFromEpic(int epicId) {
                return null;
            }

        };
        return taskManager;
    }

    public static HistoryManager getDefaultHistory(){
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }
}

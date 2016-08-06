package feature.test;

import com.codeborne.selenide.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;


import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static feature.test.TodoMVCTest.TaskType.ACTIVE;
import static feature.test.TodoMVCTest.TaskType.COMPLETED;

/**
 * Created by barocko on 7/25/2016.
 */
public class TodoMVCTest extends AtTodoMVCPasgeWithClearedDataAfterEachTest {

    @BeforeClass
    public static void setTime() {
        Configuration.timeout = 20000;
    }

    @Test
    public void testTaskLifeCycle() {

        add("1");
        toggle("1");
        assertTasksAre("1");

        filterActive();
        assertNoVisibleTasks();
        add("2");
        assertVisibleTasksAre("2");
        toggleAll();
        assertNoVisibleTasks();

        filterCompleted();
        assertVisibleTasksAre("1", "2");
        //reopen
        toggle("2");
        assertVisibleTasksAre("1");
        clearCompleted();
        assertNoVisibleTasks();

        filterAll();
        assertTasksAre("2");
        assertItemsLeft(1);
    }

    @Test
    public void testEditAtAll() {
        //precondition-added tasks
        add("1", "2");

        edit("1", "1 edited");

        assertTasksAre("1 edited", "2");
        assertItemsLeft(2);
    }

    @Test
    public void testDeleteAtActive() {
        //precondition-added tasks
        add("1", "2");
        filterActive();

        delete("1");

        assertTasksAre("2");
        assertItemsLeft(1);
    }

    @Test
    public void testCancelEditAtCompleted() {
        add("1", "2");
        toggleAll();
        filterCompleted();

        cancelEdit("2", "to be canceled");

        assertTasksAre("1", "2");
        assertItemsLeft(0);
    }

    @Test
    public void testing() {
        given(new Task("a", COMPLETED), new Task("b", ACTIVE));
        assertTasksAre("a", "b");
    }

    @Test
    public void testing2() {
        givenAtActive(new Task("a", COMPLETED), new Task("b", ACTIVE));
        assertVisibleTasksAre("b");

    }

    @Test
    public void testing3() {
        givenAtCompleted(new Task("a", COMPLETED), new Task("b", ACTIVE));
        assertVisibleTasksAre("a");
    }

    @Test
    public void testing4() {
        givenAtActive(createTask(ACTIVE, "a"), createTask(ACTIVE, "b"));
        assertTasksAre("a", "b");
    }

    @Test
    public void testing5() {
        given(createTask(ACTIVE, "a"), createTask(ACTIVE, "b"));

    }

    @Test
    public void testing6() {
        givenAtCompleted(createTask(ACTIVE, "a"), createTask(COMPLETED, "b"));
        assertVisibleTasksAre("b");

    }

    ElementsCollection tasks = $$("#todo-list li");

    @Step
    private void clearCompleted() {
        $("#clear-completed").click();
    }

    @Step
    private void add(String... taskTexts) {
        for (String text : taskTexts) {
            $("#new-todo").setValue(text).pressEnter();
        }
    }

    @Step
    private void delete(String taskText) {
        tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }

    @Step
    private void toggle(String taskText) {
        tasks.find(exactText(taskText)).$(".toggle").click();
    }

    @Step
    private void toggleAll() {
        $("#toggle-all").click();
    }

    @Step
    private void assertTasksAre(String... taskTexts) {
        tasks.shouldHave(exactTexts(taskTexts));
    }

    @Step
    private void assertNoTasks() {
        tasks.shouldBe(empty);
    }

    @Step
    private SelenideElement startEdit(String oldTaskText, String newTaskText) {
        tasks.find(exactText(oldTaskText)).doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue(newTaskText);
    }

    @Step
    private void cancelEdit(String oldTaskText, String newTaskText) {
        startEdit(oldTaskText, newTaskText).pressEscape();
    }

    @Step
    private void edit(String oldTaskText, String newTaskText) {
        startEdit(oldTaskText, newTaskText).pressEnter();
    }

    @Step
    private void filterActive() {
        $(By.linkText("Active")).click();
    }

    @Step
    private void filterCompleted() {
        $(By.linkText("Completed")).click();
    }

    @Step
    private void filterAll() {
        $(By.linkText("All")).click();
    }

    @Step
    private void assertItemsLeft(Integer count) {
        $("#todo-count>strong").shouldHave(exactText((count.toString())));
    }

    @Step
    private void assertNoVisibleTasks() {
        tasks.filter(visible).shouldBe(empty);
    }

    @Step
    private void assertVisibleTasksAre(String... taskTexts) {
        tasks.filter(visible).shouldHave(exactTexts(taskTexts));
    }


    @Step
    public void given(Task... tasks) {
        List<String> taskList = new ArrayList<>();
        for (Task task : tasks) {
            taskList.add("{" + task.taskType + ",\\\"title\\\":\\\"" + task.taskText + "\\\"}");
        }
        executeJavaScript("localStorage.setItem(\"todos-troopjs\",\"[" + String.join(",", taskList) + "]\")");
        System.out.println("localStorage.setItem(\"todos-troopjs\",\"[" + String.join(",", taskList) + "]\")");
        refresh();
    }

    public void givenAtActive(Task... tasks) {
        given(tasks);
        filterActive();
    }

    public void givenAtCompleted(Task... tasks) {
        given(tasks);
        filterCompleted();
    }

    public static Task[] aTasks(TaskType taskType, String... taskNames) {
        Task[] tasks = new Task[taskNames.length];
        for (int i = 0; i < taskNames.length; i++) {
            tasks[i] = new Task(taskNames[i], taskType);
        }
        return tasks;
    }

    public static Task createTask(TaskType taskType, String taskText) {
        return new Task(taskText, taskType);
    }

    public void givenAtActive(TaskType taskType, String taskTexts) {
        givenAtActive(createTask(taskType, taskTexts));
    }

    public void givenAtAll(TaskType taskType, String taskTexts) {
        given(createTask(taskType, taskTexts));
    }

    public void givenAtCompleted(TaskType taskType, String taskTexts) {
        givenAtCompleted(createTask(taskType, taskTexts));
    }

    public static class Task {
        private String taskText;
        private TaskType taskType;

        public Task(String taskText, TaskType taskType) {
            this.taskText = taskText;
            this.taskType = taskType;
        }

        @Override
        public String toString() {
            return "{" + taskType + ",'title':'" + taskText + "'}";
        }
    }

    public enum TaskType {
        ACTIVE("\\\"completed\\\":false"), COMPLETED("\\\"completed\\\":true");

        private String taskStatus;

        @Override
        public String toString() {
            return taskStatus;

        }

        TaskType(String taskStatus) {
            this.taskStatus = taskStatus;
        }
    }

//    public enum Filter {
//        ACTIVE("/#/all"), COMPLETED("/#/completed"), ALL("/#");
//
//        String s;
//
//        Filter(String s) {
//            this.s = s;
//        }
//        public String link() {
//            return "https://todomvc4tasj.herokuapp.com" + s;
//        }
//
//    }
}
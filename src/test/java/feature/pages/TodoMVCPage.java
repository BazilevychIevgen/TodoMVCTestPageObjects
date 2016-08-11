package feature.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
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
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.WebDriverRunner.url;

/**
 * Created by barocko on 8/10/2016.
 */
public class TodoMVCPage {

    public ElementsCollection tasks = $$("#todo-list li");

    public SelenideElement newTodo = $("#new-todo");

    @Step
    public void clearCompleted() {
        $("#clear-completed").click();
    }

    @Step
    public void add(String... taskTexts) {
        for (String text : taskTexts) {
            newTodo.setValue(text).pressEnter();
        }
    }


    public void delete(String taskText) {
        tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }

    public void toggle(String taskText) {
        tasks.find(exactText(taskText)).$(".toggle").click();
    }

    public void toggleAll() {
        $("#toggle-all").click();
    }

    public void assertTasksAre(String... taskTexts) {
        tasks.shouldHave(exactTexts(taskTexts));
    }

    public void assertNoTasks() {
        tasks.shouldBe(empty);
    }

    public SelenideElement startEdit(String oldTaskText, String newTaskText) {
        tasks.find(exactText(oldTaskText)).doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue(newTaskText);
    }

    public void cancelEdit(String oldTaskText, String newTaskText) {
        startEdit(oldTaskText, newTaskText).pressEscape();
    }

    public void confirmEditByPressTab(String oldTaskText, String newTaskText) {
        startEdit(oldTaskText, newTaskText).pressTab();
    }

    public void confirmEditByClickOutside(String oldTaskText, String newTaskText) {
        startEdit(oldTaskText, newTaskText);
        newTodo.click();
    }

    public void edit(String oldTaskText, String newTaskText) {
        startEdit(oldTaskText, newTaskText).pressEnter();
    }

    public void filterActive() {
        $(By.linkText("Active")).click();
    }

    public void filterCompleted() {
        $(By.linkText("Completed")).click();
    }

    public void filterAll() {
        $(By.linkText("All")).click();
    }

    public void assertItemsLeft(Integer count) {
        $("#todo-count>strong").shouldHave(exactText((count.toString())));
    }

    public void assertNoVisibleTasks() {
        tasks.filter(visible).shouldBe(empty);
    }

    public void assertVisibleTasksAre(String... taskTexts) {
        tasks.filter(visible).shouldHave(exactTexts(taskTexts));
    }

    public void ensureAppIsOpened() {
        if (!(url().equals("https://todomvc4tasj.herokuapp.com"))) {
            open("https://todomvc4tasj.herokuapp.com");
        }
    }

    public void given(Task... tasks) {
        ensureAppIsOpened();
        List<String> taskList = new ArrayList<>();
        for (Task task : tasks) {
            taskList.add("{" + task.taskType + ",\\\"title\\\":\\\"" + task.taskText + "\\\"}");
        }
        executeJavaScript("localStorage.setItem(\"todos-troopjs\",\"[" + String.join(",", taskList) + "]\")");
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

    public Task[] aTasks(TaskType taskType, String... taskText) {
        Task[] tasks = new Task[taskText.length];
        for (int i = 0; i < taskText.length; i++) {
            tasks[i] = new Task(taskType, taskText[i]);
        }
        return tasks;
    }

    public Task aTask(TaskType taskType, String taskText) {
        return new Task(taskType, taskText);
    }

    public void givenAtActive(TaskType taskType, String... taskText) {
        givenAtActive(aTasks(taskType, taskText));
    }

    public void givenAtAll(TaskType taskType, String... taskText) {
        given(aTasks(taskType, taskText));
    }

    public void givenAtCompleted(TaskType taskType, String... taskText) {
        givenAtCompleted(aTasks(taskType, taskText));
    }

    public class Task {
        public String taskText;
        public TaskType taskType;

        public Task(TaskType taskType, String taskText) {
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

        public String taskStatus;

        @Override
        public String toString() {
            return taskStatus;

        }

        TaskType(String taskStatus) {
            this.taskStatus = taskStatus;
        }


    }
}

package ua.net.itlabs;

import com.codeborne.selenide.*;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Created by barocko on 7/9/2016.
 */
public class TodoMVCTest {

    @Test
    public void testTaskLifeCycle() {

        open("https://todomvc4tasj.herokuapp.com/#/");

        add("1");
        edit("1", "1 edited");
        toggle("1 edited");
        assertTasksAre("1 edited");

        filterActive();
        assertNoVisibleTasks();
        add("2");
        assertVisibleTasksAre("2");
        toggleAll();
        assertNoVisibleTasks();

        filterCompleted();
        assertVisibleTasksAre("1 edited", "2");
        cancelEdit("2", "777");
        assertVisibleTasksAre("1 edited", "2");
        //reopen
        toggle("2");
        assertVisibleTasksAre("1 edited");
        clearCompleted();
        assertNoVisibleTasks();

        filterAll();
        assertTasksAre("2");
        assertItemsLeft(1);
        delete("2");
        assertNoTasks();
    }

    ElementsCollection tasks = $$("#todo-list li");

    private void clearCompleted() {
        $("#clear-completed").click();
    }

    private void add(String... taskTexts) {
        for (String text : taskTexts) {
            $("#new-todo").setValue(text).pressEnter();
        }
    }

    private void delete(String taskText) {
        tasks.find(exactText(taskText)).hover().$(".destroy").click();
    }

    private void toggle(String taskText) {
        tasks.find(exactText(taskText)).$(".toggle").click();
    }

    private void toggleAll() {
        $("#toggle-all").click();
    }

    private void assertTasksAre(String... taskTexts) {
        tasks.shouldHave(exactTexts(taskTexts));
    }

    private void assertNoTasks() {
        tasks.shouldBe(empty);
    }

    private SelenideElement startEdit(String oldTaskText, String newTaskText) {
        tasks.find(exactText(oldTaskText)).doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue(newTaskText);
    }

    private void cancelEdit(String oldTaskText, String newTaskText) {
        startEdit(oldTaskText, newTaskText).pressEscape();
    }

    private void edit(String oldTaskText, String newTaskText) {
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

    private void assertItemsLeft(Integer count) {
        $("#todo-count>strong").shouldHave(exactText((count.toString())));
    }

    private void assertNoVisibleTasks() {
        tasks.filter(visible).shouldBe(empty);
    }

    private void assertVisibleTasksAre(String... taskTexts) {
        tasks.filter(visible).shouldHave(exactTexts(taskTexts));
    }
}












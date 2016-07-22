package ua.net.itlabs;

import com.codeborne.selenide.*;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
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
        editTask("1", "1 edited");

        switchTo("Active");
        cancelEdit("1 edited", "45");
        toggle("1 edited");
        add("2");
        toggleAll();
        assertNoVisibleTasks();

        switchTo("Completed");
        assertVisibleTasks("1 edited", "2");
        reopen("1 edited");
        switchTo("All");
        assetItemsLeft("1 item left");
        delete("1 edited");
        assertTasksAre("2");
        clearCompleted();
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

    private SelenideElement editTask(String oldTaskText, String newTaskText) {
        tasks.find(exactText(oldTaskText)).doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue("1 edited").pressEnter();
    }

    private void switchTo(String taskTexts) {
        $$("#filters li").find(exactText(taskTexts)).click();
    }

    private SelenideElement cancelEdit(String taskText, String editText) {
        tasks.find(exactText(taskText)).doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue("45").pressEscape();
    }

    private void reopen(String taskText) {
        tasks.find(exactText(taskText)).$(".toggle").doubleClick();
    }

    private void assetItemsLeft(String taskText) {
        $("#todo-count").shouldHave(exactText(taskText));
    }

    private void assertNoVisibleTasks() {
        tasks.filter(visible).shouldBe(empty);

    }

    private void assertVisibleTasks(String... taskTexts) {
        tasks.filter(visible).shouldHave(exactTexts(taskTexts));

    }
}












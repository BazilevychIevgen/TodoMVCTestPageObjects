package ua.net.itlabs;

import com.codeborne.selenide.*;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
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
        toggle("1 edited");

        filterActive();
        assertNoVisibleTasksAre();
        add("2");
        assertVisibleTasksAre("2");
        toggle("2");

        filterCompleted();
        assertTasksAre("1 edited", "2");
        cancelEdit("2", "777");
        //reopenAll
        toggleAll();
        assertNoVisibleTasksAre();

        filterAll();
        assertTasksAre("1 edited","2");
        toggle("1 edited");
        clearCompleted();
        assertTasksAre("2");
        assertItemsLeft(1);
        delete("2");
        assertNoTasksAre();


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

    private void assertNoTasksAre() {
        tasks.shouldBe(empty);
    }

    private SelenideElement editTask(String oldTaskText, String newTaskText) {
        tasks.find(exactText(oldTaskText)).doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue(newTaskText).pressEnter();
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

    private SelenideElement cancelEdit(String taskText, String editText) {
        tasks.find(exactText(taskText)).doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue(editText).pressEscape();
    }

    private void assertItemsLeft(Integer a) {
        $("#todo-count>strong").shouldHave(exactText((a.toString())));
    }

    private void assertNoVisibleTasksAre() {
        tasks.filter(visible).shouldBe(empty);

    }

    private void assertVisibleTasksAre(String... taskTexts) {
        tasks.filter(visible).shouldHave(exactTexts(taskTexts));

    }
}












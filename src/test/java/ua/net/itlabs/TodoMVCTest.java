package ua.net.itlabs;

import com.codeborne.selenide.*;
import org.junit.Test;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

/**
 * Created by barocko on 7/9/2016.
 */
public class TodoMVCTest {

    @Test
    public void testTaskLifeCycle() {

        open("https://todomvc4tasj.herokuapp.com/#/");

        add("1");
        add("2");

        editTask("2");

        //complete & clear
        toggle("2 edited");
        clearCompleted();

        switchTo("Active");
        escape("1");

        toggleAll();
        switchTo("Completed");
        reopenall();
        switchTo("All");
        count("1 item left");
        delete("1");
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

    private SelenideElement editTask(String taskText){
    tasks.find(exactText(taskText)).doubleClick();
    return tasks.find(cssClass("editing")).find(".edit").setValue("2 edited").pressEnter();
}
private void switchTo(String taskTexts){
    $$("#filters li").find(exactText(taskTexts)).click();
}

    private SelenideElement escape(String taskText){
        tasks.find(exactText(taskText)).doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue("45").pressEscape();
    }
private void reopenall(){
    $("#toggle-all").doubleClick();
}
private void count(String  taskText){
    $("#todo-count").shouldHave(exactText(taskText));
}
}












package feature.test;

import com.codeborne.selenide.*;
import feature.test.pages.TodoMVCPage;
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
import static com.codeborne.selenide.WebDriverRunner.url;
import static feature.test.pages.TodoMVCPage.TaskType.ACTIVE;
import static feature.test.pages.TodoMVCPage.TaskType.COMPLETED;
import static feature.test.pages.TodoMVCPage.aTask;

/**
 * Created by barocko on 7/25/2016.
 */
public class TodoMVCTestLifeCycle extends BaseTest {

    TodoMVCPage page = new TodoMVCPage();

    @BeforeClass
    public static void setTimeout() {
        Configuration.timeout = 20000;
    }

    @Test
    public void testTaskLifeCycle() {
        page.given();

        page.add("1");
        page.toggle("1");
        page.assertTasksAre("1");

        page.filterActive();
        page.assertNoVisibleTasks();
        page.add("2");
        page.assertVisibleTasksAre("2");
        page.toggleAll();
        page.assertNoVisibleTasks();

        page.filterCompleted();
        page.assertVisibleTasksAre("1", "2");
        //reopen
        page.toggle("2");
        page.assertVisibleTasksAre("1");
        page.clearCompleted();
        page.assertNoVisibleTasks();

        page.filterAll();
        page.assertTasksAre("2");
        page.assertItemsLeft(1);
    }

}


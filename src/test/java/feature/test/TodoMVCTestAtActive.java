package feature.test;

import feature.test.pages.TodoMVCPage;
import org.junit.Test;

import static feature.test.pages.TodoMVCPage.TaskType.ACTIVE;
import static feature.test.pages.TodoMVCPage.TaskType.COMPLETED;
import static feature.test.pages.TodoMVCPage.aTask;

/**
 * Created by barocko on 8/10/2016.
 */
public class TodoMVCTestAtActive extends BaseTest {

    TodoMVCPage page=new TodoMVCPage();

    @Test
    public void testEditAtActive() {
        page.givenAtActive(aTask(COMPLETED, "1"), aTask(ACTIVE, "2"));

        page.edit("2", "2 edited");

        page.assertVisibleTasksAre("2 edited");
        page.assertItemsLeft(1);
    }

    @Test
    public void testDeleteAtActive() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.delete("2");

        page.assertTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testSwitchFilterToAllAtActive() {
        page.givenAtActive(aTask(ACTIVE, "1"), aTask(COMPLETED, "2"));

        page.filterAll();

        page.assertTasksAre("1", "2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testDeleteByEmptyingTextAtActive() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.edit("2", "");

        page.assertTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testClearCompletedAtActive() {
        page.givenAtActive(aTask(ACTIVE, "1"), aTask(COMPLETED, "2"));

        page.clearCompleted();

        page.assertTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testCancelEditAtActive() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.cancelEdit("2", "2 edited");

        page.assertTasksAre("1", "2");
        page.assertItemsLeft(2);
    }

    @Test
    public void testCompleteAtActive() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.toggle("2");

        page.assertVisibleTasksAre("1");
        page.assertItemsLeft(1);
    }

}

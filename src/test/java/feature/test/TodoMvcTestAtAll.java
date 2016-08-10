package feature.test;

import feature.test.pages.TodoMVCPage;
import org.junit.Test;

import static feature.test.pages.TodoMVCPage.TaskType.ACTIVE;
import static feature.test.pages.TodoMVCPage.TaskType.COMPLETED;
import static feature.test.pages.TodoMVCPage.aTask;

/**
 * Created by barocko on 8/10/2016.
 */
public class TodoMvcTestAtAll extends BaseTest {

    TodoMVCPage page=new TodoMVCPage();


    @Test
    public void testEditAtAll() {
        page.givenAtAll(ACTIVE, "1", "2");

        page.edit("1", "1 edited");

        page.assertTasksAre("1 edited", "2");
        page.assertItemsLeft(2);
    }

    @Test
    public void testDeleteAtAll() {
        page.givenAtAll(COMPLETED, "1");

        page.delete("1");

        page.assertNoTasks();
    }

    @Test
    public void testCompleteAllAtAll() {
        page.givenAtAll(ACTIVE, "1", "2");

        page.toggleAll();

        page.assertTasksAre("1", "2");
        page.assertItemsLeft(0);
    }

    @Test
    public void testClearCompletedAtAll() {
        page.given(aTask(COMPLETED, "1"), aTask(ACTIVE, "2"));

        page.clearCompleted();

        page.assertTasksAre("2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testSwitchFilterToCompletedAtAll() {
        page.given(aTask(COMPLETED, "1"), aTask(ACTIVE, "2"));

        page.filterCompleted();

        page.assertVisibleTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testCancelEditAtAll() {
        page.givenAtAll(ACTIVE, "1");

        page.cancelEdit("1", "1 editing");

        page.assertTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testReopenAtAll() {
        page.givenAtAll(COMPLETED, "1");

        page.toggle("1");

        page.assertTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testConfirmEditByPressTabAtAll() {
        page.givenAtAll(COMPLETED, "1");

        page.confirmEditByPressTab("1", "1 edited");

        page.assertTasksAre("1 edited");
        page.assertItemsLeft(0);
    }

}

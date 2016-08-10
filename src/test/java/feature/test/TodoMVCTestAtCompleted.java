package feature.test;

import feature.test.pages.TodoMVCPage;
import org.junit.Test;

import static feature.test.pages.TodoMVCPage.TaskType.ACTIVE;
import static feature.test.pages.TodoMVCPage.TaskType.COMPLETED;
import static feature.test.pages.TodoMVCPage.aTask;

/**
 * Created by barocko on 8/10/2016.
 */
public class TodoMVCTestAtCompleted extends BaseTest {

    TodoMVCPage page = new TodoMVCPage();

    @Test
    public void testEditAtCompleted() {
        page.givenAtCompleted(aTask(COMPLETED, "1"), aTask(ACTIVE, "2"));

        page.edit("1", "1 edited");

        page.assertVisibleTasksAre("1 edited");
        page.assertItemsLeft(1);
    }

    @Test
    public void testDeleteAtCompleted() {
        page.givenAtCompleted(aTask(COMPLETED, "1"), aTask(ACTIVE, "2"));

        page.delete("1");

        page.assertNoVisibleTasks();
        page.assertItemsLeft(1);
    }

    @Test
    public void testSwitchToActiveFilterAtCompleted() {
        page.givenAtCompleted(aTask(COMPLETED, "1"), aTask(ACTIVE, "2"));

        page.filterActive();

        page.assertVisibleTasksAre("2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testCancelEditAtCompleted() {
        page.givenAtCompleted(aTask(ACTIVE, "1"), aTask(COMPLETED, "2"));

        page.cancelEdit("2", "to be canceled");

        page.assertVisibleTasksAre("2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testConfirmEditByClickOutsideAtCompleted() {
        page.givenAtCompleted(aTask(COMPLETED, "1"), aTask(ACTIVE, "2"));

        page.confirmEditByClickOutside("1", "1 edited");

        page.assertVisibleTasksAre("1 edited");
        page.assertItemsLeft(1);
    }

    @Test
    public void testReopenAllAtCompleted() {
        page.givenAtCompleted(COMPLETED, "1", "2");

        page.toggleAll();

        page.assertNoVisibleTasks();
        page.assertItemsLeft(2);
    }

}

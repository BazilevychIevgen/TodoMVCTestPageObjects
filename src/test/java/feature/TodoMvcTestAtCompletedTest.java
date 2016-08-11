package feature;

import feature.pages.TodoMvcPage;
import org.junit.Test;


import static feature.pages.TodoMvcPage.TaskType.ACTIVE;
import static feature.pages.TodoMvcPage.TaskType.COMPLETED;


/**
 * Created by barocko on 8/10/2016.
 */
public class TodoMvcTestAtCompletedTest extends BaseTest {

    TodoMvcPage page = new TodoMvcPage();

    @Test
    public void testEdit() {
        page.givenAtCompleted(page.aTask(COMPLETED, "1"), page.aTask(ACTIVE, "2"));

        page.edit("1", "1 edited");

        page.assertVisibleTasksAre("1 edited");
        page.assertItemsLeft(1);
    }

    @Test
    public void testDelete() {
        page.givenAtCompleted(page.aTask(COMPLETED, "1"), page.aTask(ACTIVE, "2"));

        page.delete("1");

        page.assertNoVisibleTasks();
        page.assertItemsLeft(1);
    }

    @Test
    public void testSwitchToActiveFilter() {
        page.givenAtCompleted(page.aTask(COMPLETED, "1"), page.aTask(ACTIVE, "2"));

        page.filterActive();

        page.assertVisibleTasksAre("2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testCancelEdit() {
        page.givenAtCompleted(page.aTask(ACTIVE, "1"), page.aTask(COMPLETED, "2"));

        page.cancelEdit("2", "to be canceled");

        page.assertVisibleTasksAre("2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testConfirmEditByClickOutside() {
        page.givenAtCompleted(page.aTask(COMPLETED, "1"), page.aTask(ACTIVE, "2"));

        page.confirmEditByClickOutside("1", "1 edited");

        page.assertVisibleTasksAre("1 edited");
        page.assertItemsLeft(1);
    }

    @Test
    public void testReopenAll() {
        page.givenAtCompleted(COMPLETED, "1", "2");

        page.toggleAll();

        page.assertNoVisibleTasks();
        page.assertItemsLeft(2);
    }

}

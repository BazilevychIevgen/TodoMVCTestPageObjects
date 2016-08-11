package feature;

import feature.pages.TodoMVC;
import org.junit.Test;


import static feature.pages.TodoMVC.TaskType.ACTIVE;
import static feature.pages.TodoMVC.TaskType.COMPLETED;


/**
 * Created by barocko on 8/10/2016.
 */
public class TodoMVCTestAtAllTest extends BaseTest {

    TodoMVC page = new TodoMVC();


    @Test
    public void testEdit() {
        page.givenAtAll(ACTIVE, "1", "2");

        page.edit("1", "1 edited");

        page.assertTasksAre("1 edited", "2");
        page.assertItemsLeft(2);
    }

    @Test
    public void testDelete() {
        page.givenAtAll(COMPLETED, "1");

        page.delete("1");

        page.assertNoTasks();
    }

    @Test
    public void testCompleteAll() {
        page.givenAtAll(ACTIVE, "1", "2");

        page.toggleAll();

        page.assertTasksAre("1", "2");
        page.assertItemsLeft(0);
    }

    @Test
    public void testClearCompleted() {
        page.given(page.aTask(COMPLETED, "1"), page.aTask(ACTIVE, "2"));

        page.clearCompleted();

        page.assertTasksAre("2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testSwitchFilterToCompleted() {
        page.given(page.aTask(COMPLETED, "1"), page.aTask(ACTIVE, "2"));

        page.filterCompleted();

        page.assertVisibleTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testCancelEdit() {
        page.givenAtAll(ACTIVE, "1");

        page.cancelEdit("1", "1 editing");

        page.assertTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testReopen() {
        page.givenAtAll(COMPLETED, "1");

        page.toggle("1");

        page.assertTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testConfirmEditByPressTab() {
        page.givenAtAll(COMPLETED, "1");

        page.confirmEditByPressTab("1", "1 edited");

        page.assertTasksAre("1 edited");
        page.assertItemsLeft(0);
    }

}

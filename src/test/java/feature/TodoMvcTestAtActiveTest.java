package feature;


import feature.pages.TodoMvcPage2;
import org.junit.Test;

import static feature.pages.TodoMvcPage2.TaskType.ACTIVE;
import static feature.pages.TodoMvcPage2.TaskType.COMPLETED;


/**
 * Created by barocko on 8/10/2016.
 */
public class TodoMvcTestAtActiveTest extends BaseTest {

    TodoMvcPage2 page = new TodoMvcPage2();

    @Test
    public void testEdit() {
        page.givenAtActive(page.aTask(COMPLETED, "1"), page.aTask(ACTIVE, "2"));

        page.edit("2", "2 edited");

        page.assertVisibleTasksAre("2 edited");
        page.assertItemsLeft(1);
    }

    @Test
    public void testDelete() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.delete("2");

        page.assertTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testSwitchFilterToAll() {
        page.givenAtActive(page.aTask(ACTIVE, "1"), page.aTask(COMPLETED, "2"));

        page.filterAll();

        page.assertTasksAre("1", "2");
        page.assertItemsLeft(1);
    }

    @Test
    public void testDeleteByEmptyingText() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.edit("2", "");

        page.assertTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testClearCompleted() {
        page.givenAtActive(page.aTask(ACTIVE, "1"), page.aTask(COMPLETED, "2"));

        page.clearCompleted();

        page.assertTasksAre("1");
        page.assertItemsLeft(1);
    }

    @Test
    public void testCancelEdit() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.cancelEdit("2", "2 edited");

        page.assertTasksAre("1", "2");
        page.assertItemsLeft(2);
    }

    @Test
    public void testComplete() {
        page.givenAtActive(ACTIVE, "1", "2");

        page.toggle("2");

        page.assertVisibleTasksAre("1");
        page.assertItemsLeft(1);
    }

}

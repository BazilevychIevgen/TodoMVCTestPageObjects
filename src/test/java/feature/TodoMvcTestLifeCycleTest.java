package feature;

import feature.pages.TodoMvcPage2;
import org.junit.Test;


/**
 * Created by barocko on 7/25/2016.
 */
public class TodoMvcTestLifeCycleTest extends BaseTest {

    TodoMvcPage2 page = new TodoMvcPage2();

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


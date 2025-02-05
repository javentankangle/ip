package javen.tasklist;


import javen.parser.Parser;
import javen.task.Task;
import javen.task.ToDo;
import javen.tasklist.TaskList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {


    @Test
    public void readInputTest() {

        TaskList taskList = new TaskList(new ArrayList<Task>());


        taskList = new TaskList(new ArrayList<Task>());
        taskList.addTask(new ToDo("Task 1"));


        int result = taskList.getSize();
        assertEquals(1, result);
    }
}

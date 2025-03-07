package javen.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javen.task.Task;
import javen.task.ToDo;
import javen.tasklist.TaskList;



public class ParserTest {

    private TaskList taskList;

    @BeforeEach
    public void setUp() {

        taskList = new TaskList(new ArrayList<Task>());
        taskList.addTask(new ToDo("Task 1"));
        taskList.addTask(new ToDo("Task 2"));
        taskList.addTask(new ToDo("Task 3"));
    }

    @Test
    public void checkString_toIntegerTest() {
        String details = "2";
        String[] parts = {"todo", "2"};

        Parser parser;
        parser = new Parser();
        int result = parser.checkStringToInteger(details, parts, taskList);
        assertEquals(1, result);
    }
}

package javen.tasklist;

import javen.storage.Storage;
import javen.task.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    private Storage storage;


    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Saves taskList to local hard drive
     * If folder ".data" is not created, creates folder.
     *
     * @return ArrayList<Task>
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }


    /**
     * Add task to the list of user's task
     *
     * @return size of tasks
     */
    public int getSize() {
        return this.tasks.size();
    }


    /**
     * Add task to the list of user's task
     *
     * @param task Task that users add
     * @return a message to user
     */
    public String addTask(Task task) {

        StringBuilder sb = new StringBuilder();

        tasks.add(task);
        sb.append(
                "________________________________________\nadded:").append(task).append("\nYou have ")
                    .append(String.valueOf(this.tasks.size()))
                        .append(" tasks in the list!").append("\n________________________________________\n");

        return sb.toString();
    }


    /**
     * Prints the list of user's task
     * @return a message to user
     */
    public String listTask() {
        StringBuilder sb = new StringBuilder();

        sb.append("________________________________________\n").append("These are your tasks!\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(String.valueOf(i+1)).append(". ").append(tasks.get(i)).append("\n");
        }
        sb.append("________________________________________\n");

        return sb.toString();
    }


    /**
     * Returns a string indicating that task is deleted successfully
     * If the task item number is not indicated, returns error/guide message
     *
     * @param index The task ID
     * @return a message to user
     */
    public String deleteTask(Integer index) {
        String error = """
                ________________________________________
                Hmm... There's something wrong with your input!
                Enter the integer tagged to the task!
                input "list" to check the integer
                ________________________________________
                """;

        if (index == -1) {
            return error;
        }

        StringBuilder sb = new StringBuilder();

        try {
            Task task = this.tasks.get(index);
            this.tasks.remove(task);
            sb.append(
            """
            ________________________________________
            Task is deleted!
            """).append(task.toString()).append("\n________________________________________\n");

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            sb.append(error);
        }

        return sb.toString();
    }



    /**
     * Returns a string indicating successful mark of user's task
     * If the task item number is not indicated, returns error/guide message
     *
     * @param index The task ID
     * @return a message to user
     */
    public String markTask(Integer index) {
        String error = """
                ________________________________________
                Hmm... There's something wrong with your input!
                Enter the integer tagged to the task!
                input "list" to check the integer
                ________________________________________
                """;

        if (index == -1) {
           return error;
        }


        StringBuilder sb = new StringBuilder();

        try {
            Task task = tasks.get(index);
            task.markTask();
            sb.append(
                    """
                    ________________________________________
                    Task is marked!
                    """).append(task.toString()).append("\n________________________________________\n");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            sb.append(error);
        }

        return sb.toString();
    }


    /**
     * Returns a string indicating successful unmark of user's task
     * If the task item number is not indicated, returns error/guide message
     *
     * @param index The task ID
     * @return a message to user
     */
    public String unmarkTask(Integer index) {
        String error =  """
                ________________________________________
                Hmm... There's something wrong with your input!
                Enter the integer tagged to the task!
                input "list" to check the integer
                ________________________________________
                """;

        if (index == -1) {
            return error;
        }


        StringBuilder sb = new StringBuilder();

        try {
            Task task = tasks.get(index);
            task.unmarkTask();
            sb.append(
                    """
                    ________________________________________
                    Task is unmarked!
                    """).append(task.toString()).append("\n________________________________________\n");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            sb.append(error);
        }

        return sb.toString();
    }


    /**
     * Prints a list of task in user's task list given a keyword
     * If the keyword is blank or doesn't match any task, prints error message
     *
     * @param keyword Keyword input by user.
     * @return a message to user
     */
    public String searchTask(String keyword) {

        if (keyword == null) {
            return ("""
                ________________________________________
                Hmm... There's something wrong with your input!
                Enter the keyword that u want to search!
                input "list" to check the task u have!
                ________________________________________
                """);
        }

        StringBuilder sb = new StringBuilder();

        ArrayList<String> taskString = new ArrayList<>();
        for (int i = 0; i < this.tasks.size(); i++) {
            if (this.tasks.get(i).toString().contains(keyword)) {
                taskString.add(String.valueOf(i + 1) +
                        "." +
                        this.tasks.get(i).toString());
            }
        }


        sb.append("________________________________________\n");

        if (taskString.isEmpty()) {
            sb.append("No tasks were found under ").append(keyword).append("\n");
        } else {
            for (String task : taskString) {
                sb.append(task).append("\n");
            }
        }
        sb.append("________________________________________\n");


        return sb.toString();

    }


}
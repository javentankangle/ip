import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;


import java.util.ArrayList;
import java.util.Scanner;



public class Javen {

    public static void main(String[] args) {

        printGreeting();
        ArrayList<Task> taskList;
        taskList = loadTask();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            readInput(input, taskList);
        }

    }


    /**
     * Reads user input and runs function based one user input
     * User inputs can contain (todo, deadline, event, mark, unmark, bye, list and delete)
     * Otherwise, prints an error message for user
     *
     * @param input User input.
     * @param taskList An arraylist of task.
     */
    public static void readInput(String input, ArrayList<Task> taskList) {
        String[] parts = input.split(" ", 2);
        String command = input.split(" ", 2)[0];

        String details = "";
        if (parts.length > 1) {
            details = input.split(" ", 2)[1];
        }


        switch (command) {

        case "todo":
            if (parts.length < 2) {
                System.out.println("""
                        ________________________________________
                        Hmm... There's something wrong with your input!
                        A todo needs to have a description!
                        ________________________________________
                        """);
            } else {
                ToDo toDo = new ToDo(details);
                System.out.println(echo(toDo, taskList));
                taskList.add(toDo);
                saveTask(taskList);
            }

            break;

        case "deadline":
            String[] deadlineParts = details.split("/");

            if (deadlineParts.length < 2) {
                System.out.println("""
                        ________________________________________
                        Hmm... There's something wrong with your input!
                        A deadline requires a description and a end date!
                        Slash is necessary.
                        E.g. (deadline return book /by Sunday)
                        ________________________________________
                        """);
            } else {
                String deadlineDescription = deadlineParts[0];
                LocalDateTime deadlineEnd = parseDateTime(deadlineParts[1]);
                if (deadlineEnd == null) {
                    break;
                }
                Deadline deadline = new Deadline(deadlineDescription, deadlineEnd);
                System.out.println(echo(deadline, taskList));
                taskList.add(deadline);
                saveTask(taskList);
            }
            break;


        case "event":
            String[] event_parts = details.split("/");
            if (event_parts.length < 3) {
                System.out.println("""
                        ________________________________________
                        Hmm... There's something wrong with your input!
                        An event requires a description a start date, and a end date!
                        Slash is necessary.
                        E.g. (event project meeting /from Mon 2pm /to 4pm)
                        ________________________________________
                        """);
            } else {
                String eventDescription = event_parts[0];
                LocalDateTime eventStart = parseDateTime(event_parts[1]);
                LocalDateTime eventEnd = parseDateTime(event_parts[2]);
                if (eventStart == null || eventEnd == null) {
                    break;
                }
                Event event = new Event(eventDescription, eventStart, eventEnd);
                System.out.println(echo(event, taskList));
                taskList.add(event);
                saveTask(taskList);
            }
            break;

        case "mark":
        case "unmark":
            System.out.println(markTask(command, details, taskList, parts));
            saveTask(taskList);
            break;


        case "bye":
            printGoodbye();
            break;

        case "list":
            listItem(taskList);
            break;

        case "delete":
            System.out.println(deleteTask(details, taskList, parts));
            saveTask(taskList);
            break;

        case "find":
            searchTask(details,taskList, parts);
            break;

        default:
            System.out.println("""
                            ________________________________________
                            Hmm... There's something wrong with your input!
                            ________________________________________
                            """);
                    break;
                }
    }


    /**
     * Prints a greeting statement for the user.
     */
    public static void printGreeting() {

        System.out.println("""
                ________________________________________
                Hello! I'm Javen
                What do you need help with?
                ________________________________________
                """);
    }


    /**
     * Prints a goodbye statement to user.
     */
    public static void printGoodbye() {
        System.out.println("""
                ________________________________________
                Bye! See you soon :)
                ________________________________________
                """);
        System.exit(0);
    }


    /**
     * Prints a string to indicated successful adding of task and all user's task.
     *
     * @param task Task given by the user.
     * @param taskList ArrayList of the user's task.
     * @return String of user's task
     */
    public static String echo(Task task, ArrayList<Task> taskList) {
        return(
                "________________________________________\nadded:" +
                task +
                    "\nYou have " +
                        String.valueOf(taskList.size() + 1) +
                            " tasks in the list!" +
                                "\n________________________________________\n"

        );

    }


    /**
     * Prints the list of user's task
     *
     * @param taskList ArrayList of user's task
     */
    public static void listItem(ArrayList<Task> taskList) {
        System.out.println("________________________________________\n");
        System.out.println("These are your tasks\n");

        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(String.valueOf(i + 1) +
                    "." +
                        taskList.get(i).toString());
        }

        System.out.println("________________________________________\n");
    }


    /**
     * Returns a string indicating successful mark/unmark of user's task
     * If the task item number is not indicated, returns error/guide message
     *
     * @param command User input
     * @param item The task ID (index of task in taskList - 1)
     * @param taskList User's list of task
     * @param parts Details of the task
     *
     * @return a message indicating that task is successfully marked/unmarked
     */
    public static String markTask(String command, String item, ArrayList<Task> taskList, String[] parts) {


        String error = """
                ________________________________________
                Hmm... There's something wrong with your input!
                Enter the integer tagged to the task!
                input "list" to check the integer
                ________________________________________
                """;


        try {
            if (parts.length < 2) {
                return error;
            }

            int number = Integer.parseInt(item);
            Task task = taskList.get(number-1);

            if (command.equals("mark")) {
                task.markTask();
            } else {
                task.unmarkTask();
            }

            return("""
            ________________________________________
            Task is marked!
            """ +
                task.toString() +
                    "\n________________________________________\n");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return error;
        }

    }


    /**
     * Returns a string indicating that task is deleted successfully
     * If the task item number is not indicated, returns error/guide message
     *
     * @param item The task ID (index of task in taskList - 1)
     * @param taskList User's list of task
     * @param parts Details of the task
     *
     * @return a message indicating that task is deleted successfully
     */
    public static String deleteTask(String item, ArrayList<Task> taskList, String[] parts) {
        String error = """
                ________________________________________
                Hmm... There's something wrong with your input!
                Enter the integer tagged to the task!
                input "list" to check the integer
                ________________________________________
                """;

        try {
            if (parts.length < 2) {
                return error;
            }

            int number = Integer.parseInt(item);
            Task task = taskList.get(number-1);


            taskList.remove(task);
            return("""
            ________________________________________
            Task is deleted!
            """ +
                task.toString() +
                    "\n________________________________________\n");

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return error;
        }

    }


    /**
     * Saves taskList to local hard drive
     * If folder ".data" is not created, creates folder.
     *
     * @param taskList User's list of task
     */
    public static void saveTask(ArrayList<Task> taskList) {
        Path folderPath = Paths.get(".data");

        try {
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
                System.out.println("Folder created: " + folderPath.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(".data/duke.txt"))) {
            oos.writeObject(taskList);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }


    /**
     * Returns user's list of task loaded from hard drive
     * If file is empty, file is not available create an empty arraylist of task
     *
     * @return User's list of task
     */
    public static ArrayList<Task> loadTask() {

        ArrayList<Task> taskList;
        String path = ".data/duke.txt";
        File file = new File(path);

        if (file.exists() && file.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
                taskList = (ArrayList<Task>) ois.readObject();
                return taskList;
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading tasks: " + e.getMessage());
                taskList = new ArrayList<>();
                return taskList;
            }
        } else {
            taskList = new ArrayList<>();
            return taskList;
        }
    }


    /**
     * Prints a list of task in user's task list given a keyword
     * If the keyword is blank or doesn't match any task, prints error message
     *
     * @param item Keyword input by user.
     * @param taskList User's list of task.
     * @param parts details of user's input
     */
    public static void searchTask(String item, ArrayList<Task> taskList, String[] parts) {
        String error = """
                ________________________________________
                Hmm... There's something wrong with your input!
                Enter the keyword that u want to search!
                input "list" to check the task u have!
                ________________________________________
                """;


        if (parts.length < 2) {
            System.out.println(error);
        } else {
            ArrayList<String> taskString = new ArrayList<>();
            for (int i = 0; i < taskList.size(); i++) {
                if (taskList.get(i).toString().contains(item)) {
                    taskString.add(String.valueOf(i + 1) +
                            "." +
                            taskList.get(i).toString());
                }
            }


            System.out.println("________________________________________\n");
            if (taskString.isEmpty()) {
                System.out.println("No tasks were found under "  + item);
            } else {
                for (String task : taskString) {
                    System.out.println(task);
                }
            }
            System.out.println("________________________________________\n");
        }


    }


    /**
     * Prints a list of task in user's task list given a keyword
     * If the keyword is blank or doesn't match any task, prints error message
     *
     * @param item Keyword input by user.
     * @return Parsed datetime in LocalDateTime data type
     */
    public static LocalDateTime parseDateTime(String item) {

        String stringDateTime;

        if (item.startsWith("from")) {
            stringDateTime = item.substring(5).trim();
        } else if (item.startsWith("by") || item.startsWith("to")) {
            stringDateTime = item.substring(3).trim();
        } else {
            System.out.println("""
                    ________________________________________
                    Error: Invalid format.
                    E.g. deadline borrow books /by 2022-01-05 1800
                    E.g. event meeting /from 2022-01-05 1800 /to 2022-01-05 1900
                    ________________________________________
                    """);
            return null;
        }

        if (stringDateTime.length() < 13) {
            System.out.println("________________________________________\n"
                    + "Error: " + stringDateTime
                                + ", Invalid date format. Expected format is yyyy-mm-dd HH:mm\n"
                                        + "________________________________________\n");
            return null;
        }

        try {
            String formattedInput = stringDateTime.substring(0, 10) +
                    "T" + stringDateTime.substring(11, 13) + ":" + stringDateTime.substring(13);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return LocalDateTime.parse(formattedInput, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("________________________________________\n"
                    + "Error: " + stringDateTime
                            + ", Invalid date format. Expected format is yyyy-mm-dd HH:mm\n"
                                    + "________________________________________\n");
            return null;
        }



    }
}

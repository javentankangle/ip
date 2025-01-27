import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Scanner;

public class Javen {

    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo)

        printGreeting();
        ArrayList<Task> taskList;
        taskList = loadTask();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            readInput(input, taskList);
        }

    }

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
                String deadlineEnd = deadlineParts[1];
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
                String eventStart = event_parts[1];
                String eventEnd = event_parts[2];
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

    public static void printGreeting() {

        System.out.println("""
                ________________________________________
                Hello! I'm Javen
                What do you need help with?
                ________________________________________
                """);
    }

    public static void printGoodbye() {
        System.out.println("""
                ________________________________________
                Bye! See you soon :)
                ________________________________________
                """);
        System.exit(0);
    }


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
}

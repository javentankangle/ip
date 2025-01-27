import java.util.ArrayList;
import java.util.Scanner;

public class Javen {
    private static String[] parts;

    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo)

        printGreeting();
        ArrayList<Task> taskList = new ArrayList<>();
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
            }
            break;

        case "markTask":
        case "unmarkTask":
            System.out.println(markTask(command, details, taskList, parts));
            break;


        case "bye":
            printGoodbye();
            break;

        case "list":
            listItem(taskList);
            break;

        case "delete":
            System.out.println(deleteTask(details, taskList, parts));
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

    public static void listItem(ArrayList<Task> list) {
        System.out.println("________________________________________\n");
        System.out.println("These are your tasks\n");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(String.valueOf(i + 1) +
                    "." +
                        list.get(i).toString());
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

            if (command.equals("markTask")) {
                task.markTask();
            } else {
                task.unmarkTask();
            }

            return("""
            ________________________________________\n
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
            ________________________________________\n
            Task is deleted!
            """ +
                task.toString() +
                    "\n________________________________________\n");

        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            return error;
        }

    }
}

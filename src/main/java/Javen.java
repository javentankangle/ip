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

        printgreeting();
        ArrayList<Task> TaskList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            readinput(input, TaskList);
        }

    }

    public static void readinput(String input, ArrayList<Task> TaskList) {
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
                    ToDo todo = new ToDo(details);
                    System.out.println(echo(todo, TaskList));
                    TaskList.add(todo);
                }

                break;

            case "deadline":
                String[] deadline_parts = details.split("/");

                if (deadline_parts.length < 2) {
                    System.out.println("""
                            ________________________________________
                            Hmm... There's something wrong with your input!
                            A deadline requires a description and a end date!
                            Slash is necessary.
                            E.g. (deadline return book /by Sunday)
                            ________________________________________
                            """);
                } else {
                    String deadline_description = deadline_parts[0];
                    String deadline_end = deadline_parts[1];
                    Deadline deadline = new Deadline(deadline_description, deadline_end);
                    System.out.println(echo(deadline, TaskList));
                    TaskList.add(deadline);
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
                    String event_description = event_parts[0];
                    String event_start = event_parts[1];
                    String event_end = event_parts[2];
                    Event event = new Event(event_description, event_start, event_end);
                    System.out.println(echo(event, TaskList));
                    TaskList.add(event);
                }
                break;

            case "mark":
            case "unmark":
                System.out.println(mark(command, details, TaskList, parts));
                break;


            case "bye":
                printgoodbye();
                break;

            case "list":
                listitem(TaskList);
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

    public static void printgreeting() {

        System.out.println("""
                ________________________________________
                Hello! I'm Javen
                What do you need help with?
                ________________________________________
                """);
    }

    public static void printgoodbye() {
        System.out.println("""
                ________________________________________
                Bye! See you soon :)
                ________________________________________
                """);
        System.exit(0);
    }


    public static String echo(Task task, ArrayList<Task> TaskList) {
        return(
                "________________________________________\nadded:" +
                task +
                "\nYou have " +
                String.valueOf(TaskList.size() + 1) +
                " tasks in the list!" +
                "\n________________________________________\n"

        );

    }

    public static void listitem(ArrayList<Task> list) {
        System.out.println("________________________________________\n");
        System.out.println("These are your tasks\n");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(String.valueOf(i + 1) +
                    "." +
                    list.get(i).toString());
        }

        System.out.println("________________________________________\n");
    }


    public static String mark(String command, String item, ArrayList<Task> TaskList, String[] parts) {


        String error = """
                ________________________________________
                Hmm... There's something wrong with your input!
                Enter the integer tagged to the task!
                ________________________________________
                """;


        try {
            if (parts.length < 2) {
                return error;
            }

            int number = Integer.parseInt(item);
            Task task = TaskList.get(number-1);

            if (command.equals("mark")) {
                task.mark();
            } else {
                task.unmark();
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

    public static void delete(String item, ArrayList<Task> TaskList) {
        
    }
}

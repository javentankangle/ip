import java.lang.reflect.Array;
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

        if (parts.length > 1) {
            String details = input.split(" ", 2)[1];

            switch (command) {
                case "todo":
                    ToDo todo = new ToDo(details);
                    echo(todo, TaskList);
                    TaskList.add(todo);
                    break;

                case "deadline":
                    String deadline_description = details.split("/")[0];
                    String deadline_end = details.split("/")[1];
                    Deadline deadline = new Deadline(deadline_description, deadline_end);
                    echo(deadline, TaskList);
                    TaskList.add(deadline);
                    break;

                case "event":
                    String event_description = details.split("/")[0];
                    String event_start = details.split("/")[1];
                    String event_end = details.split("/")[2];
                    Event event = new Event(event_description, event_start, event_end);
                    echo(event, TaskList);
                    TaskList.add(event);
                    break;

                case "mark":
                    mark(details, TaskList);
                    break;

                case "unmark":
                    unmark(details, TaskList);
                    break;
            }

        } else {
            switch (command) {
                case "bye":
                    printgoodbye();
                    break;

                case "list":
                    listitem(TaskList);
                    break;
            }
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


    public static void echo(Task task, ArrayList<Task> TaskList) {
        System.out.println(
                "________________________________________\n added:" +
                task +
                "\n________________________________________\n" +
                "You have " +
                String.valueOf(TaskList.size() + 1) +
                " in the list!"
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

    public static void mark(String item, ArrayList<Task> TaskList) {
        int number = Integer.parseInt(item);
        Task task = TaskList.get(number-1);
        task.mark();

        System.out.println("""
            ________________________________________\n
            Task is completed!
            """);

        System.out.println(task.toString());
        System.out.println("________________________________________\n");
    }

    public static void unmark(String item, ArrayList<Task> TaskList) {
        int number = Integer.parseInt(item);
        Task task = TaskList.get(number-1);
        task.unmark();

        System.out.println("""
            ________________________________________\n
            Task is not completed!
            """);

        System.out.println(task.toString());
        System.out.println("________________________________________\n");
    }

}

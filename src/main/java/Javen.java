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

            if (input.equals("bye")) {
                printgoodbye();
                break;
            } else if (input.equals("list")) {
                listitem(TaskList);
            } else if (input.split(" ")[0].equals("mark")) {
                mark(input.split(" ")[1], TaskList);
            } else if (input.split(" ")[0].equals("unmark")) {
                unmark(input.split(" " )[1], TaskList);
            } else {
                echo(input, TaskList);
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
    }


    public static void echo(String word, ArrayList<Task> list) {
        System.out.println(
                "________________________________________\n added:" +
                word +
                "\n________________________________________\n"
        );
        Task task = new Task(word);
        list.add(task);

    }

    public static void listitem(ArrayList<Task> list) {
        System.out.println("________________________________________\n");
        System.out.println("These are your tasks\n");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(String.valueOf(i + 1) +
                    "." +
                    list.get(i).getIcon() +
                    " " +
                    list.get(i).getDescription());
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

        System.out.println(task.getIcon() +
                " " +
                task.getDescription());

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

        System.out.println(task.getIcon() +
                " " +
                task.getDescription());

        System.out.println("________________________________________\n");
    }

}

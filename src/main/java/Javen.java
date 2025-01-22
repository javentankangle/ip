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
        ArrayList<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                printgoodbye();
                break;
            } else if (input.equals("list")) {
                listitem(list);
            } else {
                echo(input, list);
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


    public static void echo(String word, ArrayList<String> list) {
        System.out.println(
                "________________________________________\n added:" +
                word +
                "\n________________________________________\n"
        );
        list.add(word);

    }

    public static void listitem(ArrayList<String> list) {
        System.out.println("________________________________________\n");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(String.valueOf(i) + "." + list.get(i));
        }

        System.out.println("________________________________________\n");
    }
}

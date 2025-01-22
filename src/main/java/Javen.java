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

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                printgoodbye();
                break;
            } else {
                echo(input);
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


    public static void echo(String word) {
        System.out.println(
                "________________________________________\n" +
                word +
                "\n________________________________________\n"
        );
    }
}

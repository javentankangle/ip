public class Javen {
    public static void main(String[] args) {
//        String logo = " ____        _        \n"
//                + "|  _ \\ _   _| | _____ \n"
//                + "| | | | | | | |/ / _ \\\n"
//                + "| |_| | |_| |   <  __/\n"
//                + "|____/ \\__,_|_|\\_\\___|\n";
//        System.out.println("Hello from\n" + logo)

        printgreeting();
        printgoodbye();

    }

    public static void printgreeting() {

        System.out.println("""
                ________________________________________
                Hello! I'm Javen
                What can I do for you?
                ________________________________________
                """);
    }

    public static void printgoodbye() {
        System.out.println("""
                ________________________________________
                Bye. Hope to see you again soon!
                ________________________________________
                """);
    }
}

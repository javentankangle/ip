public class Ui {

    /**
     * Prints a greeting statement for the user.
     */
    public void printGreeting() {

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
    public void printGoodbye() {
        System.out.println("""
                ________________________________________
                Bye! See you soon :)
                ________________________________________
                """);
    }



    /**
     * Prints a message to user.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

}

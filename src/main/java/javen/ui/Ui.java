package javen.ui;

public class Ui {

    /**
     * Prints a greeting statement for the user.
     */
    public String printGreeting() {

        return("""
                ________________________________________
                Hello! I'm Javen
                What do you need help with?
                ________________________________________
                """);
    }


    /**
     * Prints a goodbye statement to user.
     */
    public String printGoodbye() {
        return ("""
                ________________________________________
                Bye! See you soon :)
                ________________________________________
                """);
    }



    /**
     * Prints a message to user.
     */
    public String showMessage(String message) {
        return message;
    }

}

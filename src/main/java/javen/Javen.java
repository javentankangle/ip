package javen;

import java.io.IOException;
import java.util.ArrayList;

import javen.parser.Parser;
import javen.storage.Storage;
import javen.tasklist.TaskList;
import javen.ui.Ui;



/**
 * Consist of overall project flow
 */
public class Javen {

    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private Parser parser;


    /**
     * Overall program flow
     */
    public Javen() {

        ui = new Ui();
        storage = new Storage(".data/javen.txt");
        try {
            taskList = new TaskList(storage.loadTask());
        } catch (IOException | ClassNotFoundException e) {
            taskList = new TaskList(new ArrayList<>());
        }

        parser = new Parser();
        System.out.println(ui.printGreeting());
    }

    /**
     * Takes in user input
     * @param input user input
     *
     * @return response based on user input
     */
    public String getResponse(String input) {
        return parser.readInput(input, taskList, ui, storage); // Process input and return response
    }

    /**
     * Takes in user input
     *
     * @return greeting to user
     */
    public String getGreeting() {
        return ui.printGreeting();
    }

}

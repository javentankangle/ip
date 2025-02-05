package javen;

import javen.parser.Parser;
import javen.storage.Storage;
import javen.tasklist.TaskList;
import javen.ui.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class Javen {


    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage(".data/duke.txt");
        Parser parser = new Parser();


        TaskList taskList;
        try {
            taskList = new TaskList(storage.loadTask());
        } catch (IOException | ClassNotFoundException e) {
            taskList = new TaskList(new ArrayList<>());
        }

        ui.printGreeting();
        Scanner scanner = new Scanner(System.in);


        while (true) {
            String input = scanner.nextLine();
            parser.readInput(input, taskList, ui, storage);
        }

    }

}

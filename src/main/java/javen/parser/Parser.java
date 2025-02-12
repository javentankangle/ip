package javen.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javen.storage.Storage;
import javen.task.Deadline;
import javen.task.Event;
import javen.task.ToDo;
import javen.tasklist.TaskList;
import javen.ui.Ui;




/**
 * Handles the parsing of various input
 */
public class Parser {

    private static final int MIN_DATE_LENGTH = 13;
    private static final int DATE_PART_LENGTH = 10;
    private static final int HOUR_START_INDEX = 11;
    private static final int HOUR_END_INDEX = 13;
    /**
     * Reads user input and runs function based one user input
     * User inputs can contain (todo, deadline, event, mark, unmark, bye, list and delete)
     * Otherwise, prints an error message for user
     *
     * @param input User input.
     * @param taskList An arraylist of task.
     * @param ui javen.ui to print messages
     * @param storage javen.storage to save tasks
     */
    public String readInput(String input, TaskList taskList, Ui ui, Storage storage) {
        assert input != null : "Input cannot be null";
        assert taskList != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";

        String[] parts = input.split(" ", 2);
        String command = parts[0];
        StringBuilder errorBuilder = new StringBuilder();
        String details = (parts.length > 1) ? parts[1] : "";

        switch (command) {
        case "todo":
            if (parts.length < 2) {
                return ui.showMessage("""
                        ________________________________________
                        Hmm... There's something wrong with your input!
                        A todo needs to have a description!
                        ________________________________________
                        """);
            }
            ToDo toDo = new ToDo(details);
            String message = ui.showMessage(taskList.addTask(toDo));
            storage.saveTask(taskList);
            return message;

        case "deadline":
            String[] deadlineParts = details.split("/");

            if (deadlineParts.length < 2) {
                return ui.showMessage("""
                        ________________________________________
                        Hmm... There's something wrong with your input!
                        A deadline requires a description and a end date!
                        Slash is necessary.
                        E.g. (deadline return book /by Sunday)
                        ________________________________________
                        """);
            }
            String deadlineDescription = deadlineParts[0];
            LocalDateTime deadlineEnd = parseDateTime(deadlineParts[1], errorBuilder);
            if (deadlineEnd == null) {
                String result = errorBuilder.toString();
                errorBuilder.setLength(0);
                return ui.showMessage(result);
            }
            Deadline deadline = new Deadline(deadlineDescription, deadlineEnd);
            String deadlineMessage = ui.showMessage(taskList.addTask(deadline));
            storage.saveTask(taskList);
            return deadlineMessage;

        case "event":
            String[] eventParts = details.split("/");
            if (eventParts.length < 3) {
                return ui.showMessage("""
                        ________________________________________
                        Hmm... There's something wrong with your input!
                        An event requires a description a start date, and a end date!
                        Slash is necessary.
                        E.g. (event project meeting /from Mon 2pm /to 4pm)
                        ________________________________________
                        """);
            }
            String eventDescription = eventParts[0];
            LocalDateTime eventStart = parseDateTime(eventParts[1], errorBuilder);
            LocalDateTime eventEnd = parseDateTime(eventParts[2], errorBuilder);
            if (eventStart == null || eventEnd == null) {
                String result = errorBuilder.toString();
                errorBuilder.setLength(0);
                return ui.showMessage(result);
            }
            Event event = new Event(eventDescription, eventStart, eventEnd);
            String eventMessage = ui.showMessage(taskList.addTask(event));
            storage.saveTask(taskList);
            return eventMessage;


        case "mark":
            String markMessage = ui.showMessage(taskList.markTask(checkStringToInteger(details, parts, taskList)));
            storage.saveTask(taskList);
            return markMessage;


        case "unmark":
            String unmarkMessage = ui.showMessage(taskList.unmarkTask(checkStringToInteger(details, parts, taskList)));
            storage.saveTask(taskList);
            return unmarkMessage;

        case "bye":
            return ui.printGoodbye();

        case "list":
            return ui.showMessage(taskList.listTask());


        case "delete":
            String deleteMessage = ui.showMessage(taskList.deleteTask(checkStringToInteger(details, parts, taskList)));
            storage.saveTask(taskList);
            return deleteMessage;

        case "find":
            return ui.showMessage(taskList.searchTask(checkStringToString(details, parts)));

        default:
            return ui.showMessage("""
                    ________________________________________
                    Hmm... There's something wrong with your input!
                    ________________________________________
                    """);
        }


    }


    /**
     * Saves taskList to local hard drive
     * If folder ".data" is not created, creates folder.
     *
     * @param details task description
     * @param parts user input split into arrays
     * @param taskList list of user's tasks
     * @return index in array or null if index is outofbounds
     */
    protected int checkStringToInteger(String details, String[] parts, TaskList taskList) {

        int index;

        try {
            index = Integer.parseInt(details) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }

        if (parts.length < 2 || index > taskList.getSize() || index < 0) {
            return -1;
        }

        return index;

    }


    /**
     * Saves taskList to local hard drive
     * If folder ".data" is not created, creates folder.
     *
     * @param details task description
     * @param parts user input split into arrays
     * @return keyword or null if keyword not provided
     */
    private String checkStringToString(String details, String[] parts) {

        return (parts.length < 2) ? null : details;
    }


    /**
     * Prints a list of task in user's task list given a keyword
     * If the keyword is blank or doesn't match any task, prints error message
     *
     * @param item Keyword input by user.
     * @param errorMessage strinbuilder that holds errormessages
     * @return Parsed datetime in LocalDateTime data type
     */
    public LocalDateTime parseDateTime(String item, StringBuilder errorMessage) {

        String stringDateTime;

        if (item.startsWith("from")) {
            stringDateTime = item.substring(5).trim();
        } else if (item.startsWith("by") || item.startsWith("to")) {
            stringDateTime = item.substring(3).trim();
        } else {
            errorMessage.append("""
                    ________________________________________
                    Error: Invalid format.
                    E.g. deadline borrow books /by 2022-01-05 1800
                    E.g. event meeting /from 2022-01-05 1800 /to 2022-01-05 1900
                    ________________________________________
                    """);
            return null;
        }

        if (stringDateTime.length() < 13) {
            errorMessage.append("\n________________________________________\n").append("Error: ").append(stringDateTime)
                    .append(", Invalid date format. Expected format is yyyy-mm-dd HH:mm\n")
                            .append("________________________________________\n");
            return null;
        }

        try {
            String datePart = stringDateTime.substring(0, DATE_PART_LENGTH);
            String hourPart = stringDateTime.substring(HOUR_START_INDEX, HOUR_END_INDEX);
            String minutePart = stringDateTime.substring(HOUR_END_INDEX);
            String formattedInput = datePart + "T" + hourPart + ":" + minutePart;
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return LocalDateTime.parse(formattedInput, formatter);
        } catch (DateTimeParseException e) {
            errorMessage.append("\n________________________________________\n").append("Error: ")
                    .append(stringDateTime).append(", Invalid date format. Expected format is yyyy-mm-dd HH:mm\n")
                            .append("________________________________________\n");
            return null;
        }
    }
}

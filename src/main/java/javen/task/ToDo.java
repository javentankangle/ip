package javen.task;

/**
 * Consist of specific task called todo
 */
public class ToDo extends Task {

    /**
     * Constructor that takes in a description
     *
     * @param description details of the todo
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

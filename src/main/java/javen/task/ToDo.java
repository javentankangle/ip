package javen.task;

public class ToDo extends Task {

    public ToDo(String Description) {
        super(Description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

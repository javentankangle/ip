package javen.task;

import java.io.Serial;
import java.io.Serializable;

/**
 * Consist of task
 */
public class Task implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String description;
    private Boolean isMark;

    /**
     * Constructor that takes in a description
     *
     * @param description details of the deadline
     */
    public Task(String description) {
        this.description = description;
        this.isMark = false;
    }


    /**
     * Put mark task status to be true
     */
    public void markTask() {
        this.isMark = true;
    }


    /**
     * Put mark task status to be false
     */
    public void unmarkTask() {
        this.isMark = false;
    }

    @Override
    public String toString() {
        return (isMark ? "[X] " : "[ ] ") + description;
    }
}

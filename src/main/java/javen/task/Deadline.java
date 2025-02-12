package javen.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Consist of specific task called deadline
 */
public class Deadline extends Task {

    private LocalDateTime endDate;

    /**
     * Constructor that takes in a description and a endtime in localdatetime format
     *
     * @param description details of the deadline
     * @param end the deadline in local datetime
     */
    public Deadline(String description, LocalDateTime end) {
        super(description);
        this.endDate = end;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "by: "
                + this.endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"));
    }
}

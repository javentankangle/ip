package javen.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Consist of specific task called event
 */
public class Event extends Task {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Constructor that takes in a description and a startime and endtime in localdatetime format
     *
     * @param description details of the deadline
     * @param start the start of event in local datetime
     * @param end the end of event in local datetime
     */

    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.startDate = start;
        this.endDate = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "from: "
                + this.startDate.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"))
                        + " to: " + this.endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"));
    }

}

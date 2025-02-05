import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Event(String Description, LocalDateTime start, LocalDateTime end) {
        super(Description);
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

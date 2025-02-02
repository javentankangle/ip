import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private LocalDateTime endDate;

    public Deadline(String Description, LocalDateTime end) {
        super(Description);
        this.endDate = end;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "by: "
                + this.endDate.format(DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"));
    }
}

public class Event extends Task {

    private String startDate;
    private String endDate;

    public Event(String Description, String start, String end) {
        super(Description);
        this.startDate = start;
        this.endDate = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "from: " + this.startDate + " to: " + this.endDate;
    }

}

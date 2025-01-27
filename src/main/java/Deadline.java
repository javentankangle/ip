public class Deadline extends Task {

    private String endDate;

    public Deadline(String Description, String end) {
        super(Description);
        this.endDate = end;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "by: " + this.endDate;
    }
}

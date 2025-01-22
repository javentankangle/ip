public class Deadline extends Task {

    private String end;

    public Deadline(String Description, String end) {
        super(Description);
        this.end = end;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "by: " + this.end;
    }
}

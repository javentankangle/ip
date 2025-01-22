public class Task {

    private final String description;
    private Boolean isMark;

    public Task(String description) {
        this.description = description;
        this.isMark = false;
    }

    public String getIcon() {
        if (this.isMark) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    public void mark(){
        this.isMark = true;
    }

    public void unmark(){
        this.isMark = false;
    }

    public String getDescription() {
        return this.description;
    }
}
